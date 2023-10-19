package com.calculateservice.service;

import com.calculateservice.dto.MonthlyCallServiceListDTO;

import java.util.List;

public interface MonthlyCallServiceListService {

    MonthlyCallServiceListDTO findById (Long id);

    List<MonthlyCallServiceListDTO> findAll ();
}
