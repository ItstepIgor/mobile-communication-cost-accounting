package com.calculateservice.service.impl;

import com.calculateservice.entity.*;
import com.calculateservice.repository.ResultRepository;
import com.calculateservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    private final CallService callService;

    private final MonthlyCallServiceService monthlyCallServiceService;

    private final PhoneNumberService phoneNumberService;

    private final RuleByNumberService ruleByNumberService;

    @Override
    public void calcResult(LocalDate date) {
        List<Result> results = new ArrayList<>();
        List<Call> allCalcByDate = callService.findAllByDate(date);
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);
        List<PhoneNumber> phoneNumbers = phoneNumberService.findAll();

        for (PhoneNumber phoneNumber : phoneNumbers) {


            BigDecimal callServiceSum = monthlyCallServiceByDate.stream()
                    .filter(monthlyCallService -> monthlyCallService.getPhoneNumber().getNumber() == (phoneNumber.getNumber()))
                    .map(MonthlyCallService::getSumWithNDS)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal callSum = BigDecimal.ZERO;

//            List<String> ruleOneTimeCallServiceName = ruleByNumberService.findRuleByNumber(phoneNumber.getNumber())
//                    .stream()
//                    .map(RuleByNumber::getOneTimeCallServiceName)
//                    .toList();
//            List<Call> callListByNumber = allCalcByDate.stream()
//                    .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
//                    .filter(call -> ruleOneTimeCallServiceName.contains(call.getCallService()))
//                    .toList();

            List<RuleByNumber> ruleByNumbers = ruleByNumberService.findRuleByNumber(phoneNumber.getNumber());
            for (RuleByNumber ruleByNumber : ruleByNumbers) {
                BigDecimal tempCallSum = allCalcByDate.stream()
                        .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                        .filter(call -> call.getCallService().equals(ruleByNumber.getOneTimeCallServiceName()))
                        .filter(call -> (call.getCallDateTime().toLocalTime().isBefore(ruleByNumber.getStartPayment()))
                                || (call.getCallDateTime().toLocalTime().isAfter(ruleByNumber.getEndPayment())))
                        .map(Call::getSum)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                callSum.add(tempCallSum);

                //TODO 1191137 по этому номеру продолжить проверку (посмотреть какие праввила достаються
                // разделить стрим и посмотреть какие данные выбираються или метод peek применить)
            }


//            BigDecimal callSum = callListByNumber.stream()
//                    .filter(call -> call.getShortNumber() == (phoneNumber.getNumber()))
//                    .map(Call::getSum)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            //TODO сделать константу размер НДС (0.25) или 25%
            BigDecimal callSumWithNDS = callSum.multiply(BigDecimal.valueOf(0.25)).add(callSum);

            Result result = Result.builder()
                    .ownerName(String.valueOf(phoneNumber.getNumber()))
                    .phoneNumber(phoneNumberService.findById(phoneNumber.getId()))
                    .sum(callSumWithNDS.add(callServiceSum))
                    .build();
            results.add(result);
        }
        resultRepository.saveAll(results);
    }
}
