package com.calculateservice.service;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;

import java.util.List;

public interface GroupNumberService {

    GroupNumber create(GroupNumberDTO groupNumberDTO);

    GroupNumber update(GroupNumberDTO groupNumberDTO);

    List<GroupNumber> findAll();

    GroupNumber findById(long id);

    void delete (Long id);
}
