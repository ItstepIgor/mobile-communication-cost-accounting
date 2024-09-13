package com.calculateservice.service.impl;

import com.calculateservice.dto.TransferWorkDayDTO;
import com.calculateservice.entity.TransferWorkDay;
import com.calculateservice.repository.TransferWorkDayRepository;
import com.calculateservice.service.TransferWorkDayService;
import com.calculateservice.service.mapper.TransferWorkDayListMapper;
import com.calculateservice.service.mapper.TransferWorkDayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferWorkDayServiceImpl implements TransferWorkDayService {


    private final TransferWorkDayRepository transferWorkDayRepository;

    private final TransferWorkDayListMapper transferWorkDayListMapper;

    private final TransferWorkDayMapper transferWorkDayMapper;

    @Override
    public List<TransferWorkDay> findAllTransferWorkDayByDate(LocalDate date) {

        return transferWorkDayRepository.findAllTransferWorkDayByDate(date.getYear(), date.getMonth().getValue());
    }

    @Override
    public List<TransferWorkDayDTO> findAll() {
        return transferWorkDayListMapper.toDTOList(transferWorkDayRepository.findAll());
    }

    @Override
    public void save(TransferWorkDayDTO transferWorkDayDTO) {
       transferWorkDayRepository.save(transferWorkDayMapper.toEntity(transferWorkDayDTO));

    }

    @Override
    public void delete(long id) {
        transferWorkDayRepository.deleteById(id);
    }
}
