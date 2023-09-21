package com.calculateservice.service.impl;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import com.calculateservice.repository.GroupNumberRepository;
import com.calculateservice.service.GroupNumberService;
import com.calculateservice.service.mapper.GroupNumberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupNumberServiceImpl implements GroupNumberService {


    private final GroupNumberRepository groupNumberRepository;

    private final GroupNumberMapper groupNumberMapper;

    @Override
    public GroupNumber create(GroupNumberDTO groupNumberDTO) {
       return groupNumberRepository.save(groupNumberMapper.toEntity(groupNumberDTO));
    }

    @Override
    public GroupNumber update(GroupNumberDTO groupNumberDTO) {
        return groupNumberRepository.save(groupNumberMapper.toEntity(groupNumberDTO));
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
