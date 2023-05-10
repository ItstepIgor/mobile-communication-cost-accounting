package com.calculateservice.service;

import com.calculateservice.entity.RuleByNumber;

import java.util.List;

public interface RuleByNumberService {
    List<RuleByNumber> findRuleByNumber(long number);
}
