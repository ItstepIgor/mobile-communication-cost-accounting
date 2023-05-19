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
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    private final CallService callService;

    private final TransferWorkDayService transferWorkDayService;

    private final MonthlyCallServiceService monthlyCallServiceService;

    private final PhoneNumberService phoneNumberService;

    private final LandlineNumberService landlineNumberService;

    private final RuleService ruleService;

    @Override
    public void calcResult(LocalDate date) {
        List<Result> results = new ArrayList<>();
        List<Call> allCalcByDate = callService.findAllByDate(date);
        List<String> stringListLandlineNumber = landlineNumberService.findAllLandlineNumber()
                .stream().map(LandlineNumber::getNumber).toList();
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);
        List<PhoneNumber> phoneNumbers = phoneNumberService.findAll();
        List<TransferWorkDay> transferWorkDays =
                transferWorkDayService.findAllTransferWorkDay(date);

        for (PhoneNumber phoneNumber : phoneNumbers) {

            BigDecimal bigDecimalCallSum = BigDecimal.valueOf(0.0);
            BigDecimal callServiceSum = BigDecimal.valueOf(0.0);

            if (phoneNumber.getGroupNumber().getId() != 3) {
                callServiceSum = monthlyCallServiceCalc(monthlyCallServiceByDate, phoneNumber);
            }

            double callSum = 0.0;
            if (phoneNumber.getGroupNumber().getId() == 1) {
                List<RuleOneTimeService> ruleOneTimeServices = ruleService.findRuleOneTimeService(phoneNumber.getNumber());
                for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                    BigDecimal weekDaySum =
                            getSum(getFilterOperatorAndNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                            .filter(call -> getFilterByRule(ruleOneTimeService, call))
                            .filter(call -> getFilterByTime(ruleOneTimeService, call))
                            .filter(call -> !getFilterByDay(transferWorkDays, call))
                            .toList());

                    BigDecimal weekEndSum =
                            getSum(getFilterOperatorAndNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                            .filter(call -> getFilterByRule(ruleOneTimeService, call))
                            .filter(call -> getFilterByDay(transferWorkDays, call)).toList());

                    callSum += weekEndSum.add(weekDaySum).doubleValue();

                }
                BigDecimal otherCallService =
                        getSum(getOtherCallService(allCalcByDate, stringListLandlineNumber, phoneNumber, ruleOneTimeServices));
                bigDecimalCallSum = BigDecimal.valueOf(callSum).add(otherCallService);

            } else if ((phoneNumber.getGroupNumber().getId() == 2)
                    || (phoneNumber.getGroupNumber().getId() == 4)
                    || (phoneNumber.getGroupNumber().getId() == 6)) {
                bigDecimalCallSum =
                        getSum(getFilterOperatorAndNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                        .toList());

            } else if (phoneNumber.getGroupNumber().getId() == 5) {
                List<RuleOneTimeService> ruleOneTimeServices = ruleService.findRuleOneTimeService(phoneNumber.getNumber());
                for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                    BigDecimal weekDaySum =
                            getSum(getFilterOperatorAndNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                            .filter(call -> getFilterByRule(ruleOneTimeService, call))
                            .filter(call -> getFilterByTime(ruleOneTimeService, call))
                            .toList());
                    callSum += weekDaySum.doubleValue();
                }
                BigDecimal otherCallService =
                        getSum(getOtherCallService(allCalcByDate, stringListLandlineNumber, phoneNumber, ruleOneTimeServices));
                bigDecimalCallSum = BigDecimal.valueOf(callSum).add(otherCallService);
            }

            //TODO сделать константу размер НДС (0.25) или 25%
            BigDecimal callSumWithNDS = bigDecimalCallSum.multiply(BigDecimal.valueOf(0.25)).add(bigDecimalCallSum);
            Result result = Result.builder()
                    .ownerName(String.valueOf(phoneNumber.getNumber()))
                    .phoneNumber(phoneNumberService.findById(phoneNumber.getId()))
                    .sum(callSumWithNDS.add(callServiceSum))
                    .build();
            results.add(result);
        }
        resultRepository.saveAll(results);
    }

    private static List<Call> getOtherCallService(List<Call> allCalcByDate,
                                                  List<String> stringListLandlineNumber,
                                                  PhoneNumber phoneNumber,
                                                  List<RuleOneTimeService> ruleOneTimeServices) {
        return getFilterOperatorAndNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                .filter(call -> !ruleOneTimeServices
                        .stream()
                        .map(RuleOneTimeService::getOneTimeCallServiceName)
                        .toList()
                        .contains(call.getCallService())).toList();
    }

    private static boolean getFilterByRule(RuleOneTimeService ruleOneTimeService, Call call) {
        return call.getCallService().equals(ruleOneTimeService.getOneTimeCallServiceName());
    }

    private static boolean getFilterByTime(RuleOneTimeService ruleOneTimeService, Call call) {
        return (call.getCallDateTime().toLocalTime().isBefore(ruleOneTimeService.getStartPayment()))
                || (call.getCallDateTime().toLocalTime().isAfter(ruleOneTimeService.getEndPayment()));
    }

    private boolean getFilterByDay(List<TransferWorkDay> transferWorkDays, Call call) {
        return call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || findTransferWorkDay(transferWorkDays, call.getCallDateTime().toLocalDate(), 1);
    }

    private static Stream<Call> getFilterOperatorAndNumber(List<Call> allCalcByDate,
                                                           List<String> stringListLandlineNumber,
                                                           PhoneNumber phoneNumber) {
        return allCalcByDate.stream()
                .filter(call -> call.getMobileOperator().equals("1"))
                .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                .filter(call -> !stringListLandlineNumber.contains(call.getNumber()));
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
        List<String> listMonthlyCallServiceName = ruleService
                .findRuleMonthlyService(phoneNumber.getId())
                .stream()
                .map(RuleMonthlyService::getMonthlyCallServiceName)
                .toList();


        return monthlyCallServiceByDate.stream()
                .filter(monthlyCallService -> monthlyCallService.getPhoneNumber().getNumber() == (phoneNumber.getNumber()))
                .filter(monthlyCallService -> !listMonthlyCallServiceName
                        .contains(monthlyCallService.getMonthlyCallServiceName()))
                .map(MonthlyCallService::getSumWithNDS)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
