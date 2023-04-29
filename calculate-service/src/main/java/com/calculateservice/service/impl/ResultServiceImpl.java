package com.calculateservice.service.impl;

import com.calculateservice.entity.Call;
import com.calculateservice.entity.Result;
import com.calculateservice.entity.MonthlyCallService;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.repository.ResultRepository;
import com.calculateservice.service.CallService;
import com.calculateservice.service.ResultService;
import com.calculateservice.service.MonthlyCallServiceService;
import com.calculateservice.service.PhoneNumberService;
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

    @Override
    public void calcResult(LocalDate date) {
        List<Result> results = new ArrayList<>();
        List<Call> allCalcByDate = callService.findAllByDate(date);
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);
        List<PhoneNumber> phoneNumbers = phoneNumberService.findAll();

        for (PhoneNumber phoneNumber : phoneNumbers) {
            BigDecimal callSum = allCalcByDate.stream()
                    .filter(call -> call.getShortNumber() == (phoneNumber.getNumber()))
                    .map(Call::getSum)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal callServiceSum = monthlyCallServiceByDate.stream()
                    .filter(monthlyCallService -> monthlyCallService.getPhoneNumber().getNumber() == (phoneNumber.getNumber()))
                    .map(MonthlyCallService::getSumWithNDS)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
