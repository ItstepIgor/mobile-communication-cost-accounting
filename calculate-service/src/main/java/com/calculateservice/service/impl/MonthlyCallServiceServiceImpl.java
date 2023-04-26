package com.calculateservice.service.impl;

import com.calculateservice.entity.MonthlyCallService;
import com.calculateservice.repository.MonthlyCallServiceRepository;
import com.calculateservice.service.MonthlyCallServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MonthlyCallServiceServiceImpl implements MonthlyCallServiceService {

    private final MonthlyCallServiceRepository monthlyCallServiceRepository;

    @Override
    public List<MonthlyCallService> findAllByDate(LocalDate date) {
        return monthlyCallServiceRepository.getAllCallServicesByDate(date);

    }
}
