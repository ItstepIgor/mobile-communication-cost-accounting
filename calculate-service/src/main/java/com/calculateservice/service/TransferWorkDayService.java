package com.calculateservice.service;

import com.calculateservice.dto.TransferWorkDayDTO;
import com.calculateservice.entity.TransferWorkDay;

import java.time.LocalDate;
import java.util.List;

public interface TransferWorkDayService {
    List<TransferWorkDay> findAllTransferWorkDayByDate(LocalDate date);

    List<TransferWorkDayDTO> findAll();

    void save(TransferWorkDayDTO transferWorkDayDTO);

    void delete(long id);
}
