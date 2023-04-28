package com.calculateservice.service;

import com.calculateservice.entity.GroupNumber;

import java.util.List;

public interface GroupNumberService {

    void create(GroupNumber groupNumber);

    GroupNumber update(GroupNumber groupNumber);

    List<GroupNumber> findAll();

    GroupNumber findById(long id);

    void delete (Long id);
}
