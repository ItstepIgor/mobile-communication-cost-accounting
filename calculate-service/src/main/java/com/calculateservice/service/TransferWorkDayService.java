package com.calculateservice.service;

import com.calculateservice.entity.TransferWorkDay;

import java.time.LocalDate;
import java.util.List;

public interface TransferWorkDayService {
    List<TransferWorkDay> findAllTransferWorkDay(LocalDate date);

    void save(TransferWorkDay transferWorkDay);
}
