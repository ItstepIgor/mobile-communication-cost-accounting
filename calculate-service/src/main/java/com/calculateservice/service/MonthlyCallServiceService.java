package com.calculateservice.service;

import com.calculateservice.entity.MonthlyCallService;

import java.time.LocalDate;
import java.util.List;

public interface MonthlyCallServiceService {
    List<MonthlyCallService> findAllByDate(LocalDate date);
}
