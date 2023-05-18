package com.calculateservice.service;

import com.calculateservice.entity.RuleMonthlyService;
import com.calculateservice.entity.RuleOneTimeService;

import java.util.List;

public interface RuleService {
    List<RuleOneTimeService> findRuleOneTimeService(long number);
    List<RuleMonthlyService> findRuleMonthlyService(long number);
}
