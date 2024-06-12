package com.calculateservice.service;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;

import java.util.List;

public interface GroupNumberService {

    GroupNumberDTO create(GroupNumberDTO groupNumberDTO);

    GroupNumberDTO update(GroupNumberDTO groupNumberDTO);

    List<GroupNumberDTO> findAll();

    GroupNumberDTO findById(long id);

    void delete (Long id);

    void addRuleToGroup(Long groupId, Long ruleId);

    void removeRuleFromGroup(Long groupId, Long ruleId);

    GroupNumber findGroupNumberById (long id);
}
