package com.calculateservice.service.impl;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import com.calculateservice.entity.RuleOneTimeCallService;
import com.calculateservice.repository.GroupNumberRepository;
import com.calculateservice.repository.RuleOneTimeCallServiceRepository;
import com.calculateservice.service.GroupNumberService;
import com.calculateservice.service.mapper.GroupNumberListMapper;
import com.calculateservice.service.mapper.GroupNumberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupNumberServiceImpl implements GroupNumberService {


    private final GroupNumberRepository groupNumberRepository;

    private final RuleOneTimeCallServiceRepository ruleOneTimeCallServiceRepository;

    private final GroupNumberMapper groupNumberMapper;

    private final GroupNumberListMapper groupNumberListMapper;

    @Override
    public GroupNumberDTO create(GroupNumberDTO groupNumberDTO) {
        return groupNumberMapper.toDTO(groupNumberRepository.save(groupNumberMapper.toEntity(groupNumberDTO)));
    }

    @Override
    public GroupNumberDTO update(GroupNumberDTO groupNumberDTO) {
        return groupNumberMapper.toDTO(groupNumberRepository.save(groupNumberMapper.toEntity(groupNumberDTO)));
    }

    @Override
    public List<GroupNumberDTO> findAll() {
        return groupNumberListMapper.toListDTO(groupNumberRepository.findAll());
    }

    @Override
    public GroupNumberDTO findById(long id) {
        return groupNumberMapper.toDTO(groupNumberRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        groupNumberRepository.deleteById(id);
    }

    @Override
    public void addRuleToGroup(Long groupId, Long ruleId) {
        RuleOneTimeCallService ruleOneTimeCallServices = getRuleOneTimeCallService(ruleId);

        GroupNumber groupNumber = groupNumberRepository.findById(groupId).orElse(null);

        groupNumber.addRule(ruleOneTimeCallServices);

        groupNumberRepository.save(groupNumber);

    }

    private RuleOneTimeCallService getRuleOneTimeCallService(Long id) {
        return ruleOneTimeCallServiceRepository.findById(id).orElse(null);
    }

}
