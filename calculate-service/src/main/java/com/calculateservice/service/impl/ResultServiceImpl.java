package com.calculateservice.service.impl;

import com.calculateservice.entity.*;
import com.calculateservice.repository.ResultRepository;
import com.calculateservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    private final CallService callService;

    private final TransferWorkDayService transferWorkDayService;

    private final MonthlyCallServiceService monthlyCallServiceService;

    private final PhoneNumberService phoneNumberService;

    private final RuleByNumberService ruleByNumberService;

    @Override
    public void calcResult(LocalDate date) {
        List<Result> results = new ArrayList<>();
        List<Call> allCalcByDate = callService.findAllByDate(date);
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);
        List<PhoneNumber> phoneNumbers = phoneNumberService.findAll();
        List<TransferWorkDay> transferWorkDays =
                transferWorkDayService.findAllTransferWorkDay(date);

        for (PhoneNumber phoneNumber : phoneNumbers) {

            BigDecimal bigDecimalCallSum = BigDecimal.valueOf(0.0);

            BigDecimal callServiceSum = monthlyCallServiceCalc(monthlyCallServiceByDate, phoneNumber);

            double callSum = 0.0;
            if (phoneNumber.getGroupNumber().getId() == 1) {
                List<RuleByNumber> ruleByNumbers = ruleByNumberService.findRuleByNumber(phoneNumber.getNumber());
                for (RuleByNumber ruleByNumber : ruleByNumbers) {
                    BigDecimal weekDaySum = getSum(allCalcByDate.stream()
                            .filter(call -> call.getMobileOperator().equals("1"))
                            .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                            .filter(call -> call.getCallService().equals(ruleByNumber.getOneTimeCallServiceName()))
                            .filter(call -> !(call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                                    || call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)
                                    || findTransferWorkDay(transferWorkDays, call.getCallDateTime().toLocalDate(), 1)))
                            .filter(call -> (call.getCallDateTime().toLocalTime().isBefore(ruleByNumber.getStartPayment()))
                                    || (call.getCallDateTime().toLocalTime().isAfter(ruleByNumber.getEndPayment())))
                            .toList());

                    BigDecimal weekEndSum = getSum(allCalcByDate.stream()
                            .filter(call -> call.getMobileOperator().equals("1"))
                            .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                            .filter(call -> call.getCallService().equals(ruleByNumber.getOneTimeCallServiceName()))
                            .filter(call -> (call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                                    || call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)
                                    || findTransferWorkDay(transferWorkDays, call.getCallDateTime().toLocalDate(), 1)
                            )).toList());

                    callSum += weekEndSum.add(weekDaySum).doubleValue();

                    //TODO сделать дополнительную таблицу для связи номеров с услугими за которые не нужно удерживать
                    // возможно связь нужно делать с группами.
                    // добавить запрос для вставки этой связи.
                }
                BigDecimal otherCallService = getSum(allCalcByDate.stream()
                        .filter(call -> call.getMobileOperator().equals("1"))
                        .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                        .filter(call -> !ruleByNumbers
                                .stream()
                                .map(RuleByNumber::getOneTimeCallServiceName)
                                .toList()
                                .contains(call.getCallService())).toList());
                bigDecimalCallSum = BigDecimal.valueOf(callSum).add(otherCallService);

            } else if ((phoneNumber.getGroupNumber().getId() == 2)
                    || (phoneNumber.getGroupNumber().getId() == 4)) {
                bigDecimalCallSum = getSum(allCalcByDate.stream()
                        .filter(call -> call.getMobileOperator().equals("1"))
                        .filter(call -> call.getShortNumber() == phoneNumber.getNumber()).toList());
            }


            //TODO сделать константу размер НДС (0.25) или 25%
            BigDecimal callSumWithNDS = bigDecimalCallSum.multiply(BigDecimal.valueOf(0.25)).add(bigDecimalCallSum);
            Result result = Result.builder()
                    .ownerName(String.valueOf(phoneNumber.getNumber()))
                    .phoneNumber(phoneNumberService.findById(phoneNumber.getId()))
                    .sum(callSumWithNDS/*.add(callServiceSum)*/)
                    .build();
            results.add(result);
        }
        resultRepository.saveAll(results);
    }

    private boolean findTransferWorkDay(List<TransferWorkDay> transferWorkDays, LocalDate date, int i) {
        Optional<LocalDate> first = transferWorkDays.stream()
                .filter(transferWorkDay -> transferWorkDay.getTypeTransferWorkDay().getId() == i)
                .filter(transferWorkDay -> transferWorkDay.getTransferDate().equals(date))
                .map(TransferWorkDay::getTransferDate)
                .findFirst();
        if (first.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    private BigDecimal getSum(List<Call> callList) {
        return callList.stream()
                .map(Call::getSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal monthlyCallServiceCalc(List<MonthlyCallService> monthlyCallServiceByDate, PhoneNumber phoneNumber) {
        return monthlyCallServiceByDate.stream()
                .filter(monthlyCallService -> monthlyCallService.getPhoneNumber().getNumber() == (phoneNumber.getNumber()))
                .map(MonthlyCallService::getSumWithNDS)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
