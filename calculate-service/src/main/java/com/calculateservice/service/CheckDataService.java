package com.calculateservice.service;

import com.calculateservice.dto.AllCallServiceDTO;

import java.time.LocalDate;
import java.util.List;

public interface CheckDataService {

    List<AllCallServiceDTO> checkSumMonthlyCallService(LocalDate date);
}
