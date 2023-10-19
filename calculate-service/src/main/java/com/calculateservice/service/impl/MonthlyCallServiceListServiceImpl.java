package com.calculateservice.service.impl;

import com.calculateservice.dto.MonthlyCallServiceListDTO;
import com.calculateservice.repository.MonthlyCallServiceListRepository;
import com.calculateservice.service.MonthlyCallServiceListService;
import com.calculateservice.service.mapper.MonthlyCallServiceListMapper;
import com.calculateservice.service.mapper.MonthlyCallServiceListsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MonthlyCallServiceListServiceImpl implements MonthlyCallServiceListService {

    private final MonthlyCallServiceListRepository monthlyCallServiceListRepository;

    private final MonthlyCallServiceListMapper monthlyCallServiceListMapper;

    private final MonthlyCallServiceListsMapper monthlyCallServiceListsMapper;

    @Override
    public MonthlyCallServiceListDTO findById(Long id) {
        return monthlyCallServiceListMapper.toDTO(monthlyCallServiceListRepository.findById(id).orElse(null));
    }

    @Override
    public List<MonthlyCallServiceListDTO> findAll() {
        return monthlyCallServiceListsMapper.toListDTO(monthlyCallServiceListRepository.findAll());
    }
}
