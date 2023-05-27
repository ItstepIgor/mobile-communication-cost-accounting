package com.calculateservice.service.impl;

import com.calculateservice.entity.*;
import com.calculateservice.repository.IndividualResultRepository;
import com.calculateservice.service.*;
import com.calculateservice.util.Filters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualResultServiceImpl implements IndividualResultService {

    private final IndividualResultRepository individualResultRepository;

    private final TransferWorkDayService transferWorkDayService;

    private final CallService callService;

    private final RuleService ruleService;

    private final PhoneNumberService phoneNumberService;

    private static final String PAID_CALL = "Платно";
    private static final String DAY_CALL = "Днем";
    private static final String FREE_CALL = "Бесплатно";

    @Override
    public void calcIndividualResult(LocalDate date, String number) {
        individualResultRepository.deleteAll();
        PhoneNumber phoneNumber = phoneNumberService.findPhoneNumberByNumber(Long.valueOf(number));
        long phoneId = phoneNumber.getGroupNumber().getId();
        List<RuleOneTimeService> ruleOneTimeServices = ruleService.findRuleOneTimeService(Long.parseLong(number));
        List<IndividualResult> individualResults = new ArrayList<>();
        List<Call> allCallByNumber = callService.findAllCallByNumber(date, number);
        List<TransferWorkDay> transferWorkDays = transferWorkDayService.findAllTransferWorkDay(date);
        if (!ruleOneTimeServices.isEmpty()) {
            for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                allCallByNumber.stream()
                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                        .filter(call -> !Filters.getFilterByDay(transferWorkDays, call))
                        .filter(call -> (call.getCallDateTime().toLocalTime().isAfter(ruleOneTimeService.getStartPayment()))
                                && (call.getCallDateTime().toLocalTime().isBefore(ruleOneTimeService.getEndPayment())))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, DAY_CALL)));
                allCallByNumber.stream()
                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                        .filter(call -> !Filters.getFilterByDay(transferWorkDays, call))
                        .filter(call -> Filters.getFilterByTime(ruleOneTimeService, call))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, PAID_CALL)));
                allCallByNumber.stream()
                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                        .filter(call -> Filters.getFilterByDay(transferWorkDays, call))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, PAID_CALL)));
            }
        } else {
            if (phoneId == 2 || phoneId == 4 || phoneId == 6 || phoneId == 7) {
                allCallByNumber.forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, PAID_CALL)));
            } else if (phoneId == 3) {
                allCallByNumber.forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, FREE_CALL)));
            }
        }
        individualResultRepository.saveAll(individualResults);
    }

    private IndividualResult individualResultBuilder(PhoneNumber phoneNumber, Call call, String callType) {
        return IndividualResult.builder()
                .ownerName(call.getOwnerNumber())
                .callService(call.getCallService())
                .phoneNumber(phoneNumber)
                .callDateTime(call.getCallDateTime())
                .sum(call.getSum())
                .callType(callType)
                .build();
    }
}
