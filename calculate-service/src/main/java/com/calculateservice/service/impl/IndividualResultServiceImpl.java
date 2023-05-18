package com.calculateservice.service.impl;

import com.calculateservice.entity.Call;
import com.calculateservice.entity.IndividualResult;
import com.calculateservice.entity.RuleOneTimeService;
import com.calculateservice.repository.IndividualResultRepository;
import com.calculateservice.service.CallService;
import com.calculateservice.service.IndividualResultService;
import com.calculateservice.service.PhoneNumberService;
import com.calculateservice.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualResultServiceImpl implements IndividualResultService {

    private final IndividualResultRepository individualResultRepository;

    private final CallService callService;

    private final RuleService ruleService;

    private final PhoneNumberService phoneNumberService;

    private static final String CALL_TYPE = "Платно";

    @Override
    public void calcIndividualResult(LocalDate date, String number) {
        individualResultRepository.deleteAll();
        List<RuleOneTimeService> ruleOneTimeServices = ruleService.findRuleOneTimeService(Long.parseLong(number));
        List<IndividualResult> individualResults = new ArrayList<>();
        List<Call> allCallByNumber = callService.findAllCallByNumber(date, number);
        if (!ruleOneTimeServices.isEmpty()) {
            for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                allCallByNumber.stream()
                        .filter(call -> call.getCallService().equals(ruleOneTimeService.getOneTimeCallServiceName()))
                        .filter(call -> !(call.getDayOfWeek() == 6 || call.getDayOfWeek() == 7))
                        .filter(call -> (call.getCallDateTime().toLocalTime().isAfter(ruleOneTimeService.getStartPayment()))
                                && (call.getCallDateTime().toLocalTime().isBefore(ruleOneTimeService.getEndPayment())))
                        .forEach(call -> individualResults.add(individualResultBuilder(call, "Днем")));
                allCallByNumber.stream()
                        .filter(call -> call.getCallService().equals(ruleOneTimeService.getOneTimeCallServiceName()))
                        .filter(call -> !(call.getDayOfWeek() == 6 || call.getDayOfWeek() == 7))
                        .filter(call -> (call.getCallDateTime().toLocalTime().isBefore(ruleOneTimeService.getStartPayment()))
                                || (call.getCallDateTime().toLocalTime().isAfter(ruleOneTimeService.getEndPayment())))
                        .forEach(call -> individualResults.add(individualResultBuilder(call, CALL_TYPE)));
                allCallByNumber.stream()
                        .filter(call -> call.getCallService().equals(ruleOneTimeService.getOneTimeCallServiceName()))
                        .filter(call -> (call.getDayOfWeek() == 6 || call.getDayOfWeek() == 7))
                        .forEach(call -> individualResults.add(individualResultBuilder(call, CALL_TYPE)));
            }
        } else {
            allCallByNumber.forEach(call -> individualResults.add(individualResultBuilder(call, CALL_TYPE)));
        }
        individualResultRepository.saveAll(individualResults);
    }

    private IndividualResult individualResultBuilder(Call call, String callType) {
        return IndividualResult.builder()
                .ownerName(call.getOwnerNumber())
                .callService(call.getCallService())
                .phoneNumber(phoneNumberService.findPhoneNumberByNumber(call.getShortNumber()))
                .callDateTime(call.getCallDateTime())
                .sum(call.getSum())
                .callType(callType)
                .build();
    }

    @Override
    public void save(List<IndividualResult> individualResults) {
        individualResultRepository.saveAll(individualResults);
    }
}
