package com.calculateservice.service;

import com.calculateservice.dto.RuleOneTimeCallServiceDTO;
import com.calculateservice.entity.RuleMonthlyService;
import com.calculateservice.entity.RuleOneTimeService;

import java.util.List;

public interface RuleService {
    List<RuleOneTimeService> findRuleOneTimeService(long number);
    List<RuleMonthlyService> findRuleMonthlyService(long number);


    RuleOneTimeCallServiceDTO create(RuleOneTimeCallServiceDTO groupNumberDTO);

    RuleOneTimeCallServiceDTO update(RuleOneTimeCallServiceDTO ruleOneTimeCallServiceDTO);

    List<RuleOneTimeCallServiceDTO> findAll();

    RuleOneTimeCallServiceDTO findById(long id);

    void delete (Long id);

}
