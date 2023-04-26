package com.calculateservice.service.impl;

import com.calculateservice.entity.Call;
import com.calculateservice.entity.IndividualResult;
import com.calculateservice.entity.MonthlyCallService;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.repository.IndividualResultRepository;
import com.calculateservice.service.CallService;
import com.calculateservice.service.IndividualResultService;
import com.calculateservice.service.MonthlyCallServiceService;
import com.calculateservice.service.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualResultServiceImpl implements IndividualResultService {

    private final IndividualResultRepository individualResultRepository;

    private final CallService callService;

    private final MonthlyCallServiceService monthlyCallServiceService;

    private final PhoneNumberService phoneNumberService;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDate date = LocalDate.parse("2023-03-31", dateTimeFormatter);

    @Override
    public void calcResult() {
        List<IndividualResult> individualResults = new ArrayList<>();
        List<Call> allCalcByDate = callService.findAllByDate(date);
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);
        List<PhoneNumber> phoneNumbers = phoneNumberService.findAll();
        //TODO продолжаем расчитывать результат
        for (PhoneNumber phoneNumber : phoneNumbers) {
            allCalcByDate.stream().filter(call -> call.getNumber().equals(phoneNumber))
                    .mapToDouble(value -> value.getSum().doubleValue())
                    .forEach(System.out::println);
        }
        System.out.println();
    }
}
