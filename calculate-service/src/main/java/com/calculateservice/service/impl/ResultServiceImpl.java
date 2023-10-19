package com.calculateservice.service.impl;

import com.calculateservice.dto.AllExpensesByPhoneNumberDTO;
import com.calculateservice.entity.*;
import com.calculateservice.repository.PhoneNumberRepository;
import com.calculateservice.repository.ResultRepository;
import com.calculateservice.service.*;
import com.calculateservice.util.Filters;
import com.calculateservice.util.ImportFeignClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ImportFeignClients importFeignClients;

    private final ResultRepository resultRepository;

    private final CallService callService;

    private final TransferWorkDayService transferWorkDayService;

    private final MonthlyCallServiceService monthlyCallServiceService;

    private final PhoneNumberRepository phoneNumberRepository;

    private final LandlineNumberService landlineNumberService;

    private final RuleService ruleService;

    @Override
    public void calcResult(LocalDate date) {
        List<Result> results = new ArrayList<>();
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();
        List<String> stringListLandlineNumber = landlineNumberService.findAllLandlineNumber()
                .stream().map(LandlineNumber::getNumber).toList();
        List<Call> allCalcByDate = callService.findAllByDate(date);
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);
        List<TransferWorkDay> transferWorkDays = transferWorkDayService.findAllTransferWorkDay(date);
        List<AllExpensesByPhoneNumberDTO> allExpensesByPhoneNumber =
                importFeignClients.findAllExpensesByPhoneNumberMTS(date).getBody();

        for (PhoneNumber phoneNumber : phoneNumbers) {

            BigDecimal allExpensesSum = BigDecimal.valueOf(0.0);
            BigDecimal bigDecimalCallSum = BigDecimal.valueOf(0.0);
            BigDecimal callServiceSum = BigDecimal.valueOf(0.0);

            long phoneGroupId = phoneNumber.getGroupNumber().getId();
            List<RuleOneTimeService> ruleOneTimeServices = ruleService.findRuleOneTimeService(phoneNumber.getNumber());

            if ((phoneGroupId != 3) && (phoneGroupId != 7)) {
                callServiceSum = monthlyCallServiceCalc(monthlyCallServiceByDate, phoneNumber);
            }

            double callSum = 0.0;
            switch ((int) phoneGroupId) {
                case 1 -> {
                    for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                        BigDecimal weekDaySum =
                                getSum(Filters.getFilterNumberAndLandlineNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                                        .filter(call -> Filters.getFilterByTime(ruleOneTimeService, call))
                                        .filter(call -> !Filters.getFilterByDay(transferWorkDays, call))
                                        .toList());

                        BigDecimal weekEndSum =
                                getSum(Filters.getFilterNumberAndLandlineNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                                        .filter(call -> Filters.getFilterByDay(transferWorkDays, call)).toList());

                        callSum += weekEndSum.add(weekDaySum).doubleValue();

                    }
                    BigDecimal otherCallService =
                            getSum(getOtherCallService(allCalcByDate, stringListLandlineNumber, phoneNumber, ruleOneTimeServices));
                    bigDecimalCallSum = BigDecimal.valueOf(callSum).add(otherCallService);

                }
                case 2, 4, 6 -> bigDecimalCallSum =
                        getSum(Filters.getFilterNumberAndLandlineNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                                .toList());
                case 5, 8 -> {
                    for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                        BigDecimal weekDaySum =
                                getSum(Filters.getFilterNumberAndLandlineNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                                        .filter(call -> Filters.getFilterByTime(ruleOneTimeService, call))
                                        .toList());
                        callSum += weekDaySum.doubleValue();
                    }
                    BigDecimal otherCallService =
                            getSum(getOtherCallService(allCalcByDate, stringListLandlineNumber, phoneNumber, ruleOneTimeServices));
                    bigDecimalCallSum = BigDecimal.valueOf(callSum).add(otherCallService);
                }
                case 7 -> allExpensesSum = allExpensesByPhoneNumber.stream()
                        .filter(allExpensesByPhoneNumberDTO -> allExpensesByPhoneNumberDTO.getNumber().substring(3, 12)
                                .equals(String.valueOf(phoneNumber.getNumber())))
                        .map(AllExpensesByPhoneNumberDTO::getSumWithNDS)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            BigDecimal callSumWithNDS;
            //TODO сделать константу размер НДС (0.25) или 25%, обработать ошибку если нет номера в общих расходах
            if (phoneGroupId != 7) {
                callSumWithNDS = bigDecimalCallSum.multiply(BigDecimal.valueOf(0.25)).add(bigDecimalCallSum);
            } else {
                callSumWithNDS = allExpensesSum;
            }

            Result result = Result.builder()
                    .ownerName(String.valueOf(phoneNumber.getNumber()))
                    .phoneNumber(phoneNumberRepository.findById(phoneNumber.getId()).orElse(null))
                    .invoiceDate(date)
                    .sum(callSumWithNDS.add(callServiceSum))
                    .build();
            results.add(result);
        }
        resultRepository.saveAll(results);
    }

    @Override
    public List<ResultPojo> getResult(long mobileOperatorId, LocalDate localDate) {
        return resultRepository.getResult(mobileOperatorId, localDate);
    }

    private static List<Call> getOtherCallService(List<Call> allCalcByDate,
                                                  List<String> stringListLandlineNumber,
                                                  PhoneNumber phoneNumber,
                                                  List<RuleOneTimeService> ruleOneTimeServices) {
        return Filters.getFilterNumberAndLandlineNumber(allCalcByDate, stringListLandlineNumber, phoneNumber)
                .filter(call -> !ruleOneTimeServices
                        .stream()
                        .map(RuleOneTimeService::getOneTimeCallServiceName)
                        .toList()
                        .contains(call.getCallService())).toList();
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
