package com.calculateservice.service.impl;

import com.calculateservice.entity.GroupNumber;
import com.calculateservice.repository.GroupNumberRepository;
import com.calculateservice.service.GroupNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupNumberServiceImpl implements GroupNumberService {


    private final GroupNumberRepository groupNumberRepository;

    @Override
    public void create(GroupNumber groupNumber) {
        groupNumberRepository.save(groupNumber);
    }

    @Override
    public GroupNumber update(GroupNumber groupNumber) {
        return groupNumberRepository.save(groupNumber);
    }

    @Override
    public List<GroupNumber> findAll() {
        return groupNumberRepository.findAll();
    }

    @Override
    public GroupNumber findById(long id) {
        return groupNumberRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        groupNumberRepository.deleteById(id);
    }
}
