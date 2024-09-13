package com.calculateservice.service.impl;

import com.calculateservice.entity.TypeTransferWorkDay;
import com.calculateservice.repository.TypeTransferWorkDayRepository;
import com.calculateservice.service.TypeTransferWorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeTransferWorkDayServiceImpl implements TypeTransferWorkDayService {


    private final TypeTransferWorkDayRepository typeTransferWorkDayRepository;
    @Override
    public TypeTransferWorkDay findById(long id) {
        return typeTransferWorkDayRepository.findById(id).orElse(null);
    }
}
