package com.calculateservice.service.impl;

import com.calculateservice.dto.RuleOneTimeCallServiceDTO;
import com.calculateservice.entity.RuleMonthlyService;
import com.calculateservice.entity.RuleOneTimeService;
import com.calculateservice.repository.RuleOneTimeCallServiceRepository;
import com.calculateservice.repository.RuleRepository;
import com.calculateservice.service.RuleService;
import com.calculateservice.service.mapper.RuleOneTimeCallServiceListMapper;
import com.calculateservice.service.mapper.RuleOneTimeCallServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;

    private final RuleOneTimeCallServiceRepository ruleOneTimeCallServiceRepository;


    private final RuleOneTimeCallServiceListMapper ruleOneTimeCallServiceListMapper;

    private final RuleOneTimeCallServiceMapper ruleOneTimeCallServiceMapper;




    @Override
    public List<RuleOneTimeService> findRuleOneTimeService(long number) {
        return ruleRepository.findRuleOneTimeService(number);
    }

    @Override
    public List<RuleMonthlyService> findRuleMonthlyService(long number) {
        return ruleRepository.findRuleMonthlyService(number);
    }

    @Override
    public RuleOneTimeCallServiceDTO create(RuleOneTimeCallServiceDTO ruleOneTimeCallServiceDTO) {
        return ruleOneTimeCallServiceMapper.toDTO(ruleOneTimeCallServiceRepository.save(
                ruleOneTimeCallServiceMapper.toEntity(ruleOneTimeCallServiceDTO)));
    }

    @Override
    public RuleOneTimeCallServiceDTO update(RuleOneTimeCallServiceDTO ruleOneTimeCallServiceDTO) {
        return ruleOneTimeCallServiceMapper.toDTO(ruleOneTimeCallServiceRepository.save(
                ruleOneTimeCallServiceMapper.toEntity(ruleOneTimeCallServiceDTO)));
    }

    @Override
    public List<RuleOneTimeCallServiceDTO> findAll() {
        return ruleOneTimeCallServiceListMapper.toDTOList(ruleOneTimeCallServiceRepository.findAll());
    }

    @Override
    public RuleOneTimeCallServiceDTO findById(long id) {
        return ruleOneTimeCallServiceMapper.toDTO(ruleOneTimeCallServiceRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        ruleOneTimeCallServiceRepository.deleteById(id);
    }


}
