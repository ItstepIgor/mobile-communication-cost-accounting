package com.calculateservice.service.impl;

import com.calculateservice.entity.*;
import com.calculateservice.repository.*;
import com.calculateservice.service.AddServiceAndRuleToNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddServiceAndRuleToNumberServiceImpl implements AddServiceAndRuleToNumberService {

    private final GroupNumberRepository groupNumberRepository;

    private final RuleOneTimeCallServiceRepository ruleOneTimeCallServiceRepository;

    private final OneTimeCallServiceRepository oneTimeCallServiceRepository;

    private final MonthlyCallServiceRepository monthlyCallServiceRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public void addRuleToNumber() {

        RuleOneTimeCallService ruleOneTimeCallServices = getRuleOneTimeCallService(1L);
//        RuleOneTimeCallService ruleOneTimeCallServices = RuleOneTimeCallService
//                .builder()
//                .oneTimeCallService(getOneTimeCallService(9L))
//                .ruleName("Base")
//                .startPayment(LocalTime.parse("08:00:00"))
//                .endPayment(LocalTime.parse("18:00:00"))
//                .build();
//        ruleOneTimeCallServiceRepository.save(ruleOneTimeCallServices);

        GroupNumber groupNumber = getGroupNumber(7L);

        groupNumber.addRule(ruleOneTimeCallServices);

        groupNumberRepository.save(groupNumber);
    }

    @Override
    public void addMonthlyCallServiceToNumber() {

        MonthlyCallService monthlyCallService = getMonthlyCallService(78L);

        GroupNumber groupNumber = getGroupNumber(7L);

//        phoneNumber.addMonthlyCallService(monthlyCallService);

        groupNumberRepository.save(groupNumber);

    }


    private PhoneNumber getPhoneNumber(Long id) {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    private MonthlyCallService getMonthlyCallService(Long id) {
        return monthlyCallServiceRepository.findById(id).orElse(null);
    }

    private OneTimeCallService getOneTimeCallService(Long id) {
        return oneTimeCallServiceRepository.findById(id).orElse(null);
    }

    private GroupNumber getGroupNumber(Long id) {
        return groupNumberRepository.findById(id).orElse(null);
    }

    private RuleOneTimeCallService getRuleOneTimeCallService(Long id) {
        return ruleOneTimeCallServiceRepository.findById(id).orElse(null);
    }
}

