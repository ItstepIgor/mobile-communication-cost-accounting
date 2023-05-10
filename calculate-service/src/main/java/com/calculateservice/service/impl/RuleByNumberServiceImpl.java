package com.calculateservice.service.impl;

import com.calculateservice.entity.RuleByNumber;
import com.calculateservice.repository.RuleByNumberRepository;
import com.calculateservice.service.RuleByNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RuleByNumberServiceImpl implements RuleByNumberService {

    private final RuleByNumberRepository ruleByNumberRepository;
    @Override
    public List<RuleByNumber> findRuleByNumber(long number) {
        return ruleByNumberRepository.findRuleByNumber(number);
    }
}
