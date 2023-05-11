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

            double callSum = 0.0;

            List<RuleByNumber> ruleByNumbers = ruleByNumberService.findRuleByNumber(phoneNumber.getNumber());
            for (RuleByNumber ruleByNumber : ruleByNumbers) {
                BigDecimal tempCallSum = allCalcByDate.stream()
                        .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                        .filter(call -> call.getCallService().equals(ruleByNumber.getOneTimeCallServiceName()))
                        .filter(call -> (call.getCallDateTime().toLocalTime().isBefore(ruleByNumber.getStartPayment()))
                                || (call.getCallDateTime().toLocalTime().isAfter(ruleByNumber.getEndPayment())))
                        .map(Call::getSum)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                callSum += tempCallSum.doubleValue();
                //TODO сделать дополнительную таблицу для связи номеров с услугими за которые не нужно удерживать
                // возможно связь нужно делать с группами.
                // добавить запрос для вставки этой связи.
            }


            //TODO сделать константу размер НДС (0.25) или 25%
            BigDecimal callSumWithNDS = BigDecimal.valueOf(callSum * 0.25 + callSum);

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
