package com.calculateservice.service.impl;

import com.calculateservice.entity.RuleMonthlyService;
import com.calculateservice.entity.RuleOneTimeService;
import com.calculateservice.repository.RuleRepository;
import com.calculateservice.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;
    @Override
    public List<RuleOneTimeService> findRuleOneTimeService(long number) {
        return ruleRepository.findRuleOneTimeService(number);
    }

    @Override
    public List<RuleMonthlyService> findRuleMonthlyService(long number) {
        return ruleRepository.findRuleMonthlyService(number);
    }
}
