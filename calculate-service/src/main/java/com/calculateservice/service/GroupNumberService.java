package com.calculateservice.service;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;

import java.util.List;

public interface GroupNumberService {

    GroupNumberDTO create(GroupNumberDTO groupNumberDTO);

    GroupNumber update(GroupNumberDTO groupNumberDTO);

    List<GroupNumberDTO> findAll();

    GroupNumberDTO findById(long id);

    void delete (Long id);
}
