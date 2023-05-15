package com.calculateservice.service.impl;

import com.calculateservice.entity.TransferWorkDay;
import com.calculateservice.repository.TransferWorkDayRepository;
import com.calculateservice.service.TransferWorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferWorkDayServiceImpl implements TransferWorkDayService {


    private final TransferWorkDayRepository transferWorkDayRepository;

    @Override
    public List<TransferWorkDay> findAllTransferWorkDay(LocalDate date) {

        return transferWorkDayRepository.findAllTransferWorkDayByDate(date.getYear(), date.getMonth().getValue());
    }

    @Override
    public void save(TransferWorkDay transferWorkDay) {

    }
}
