package com.calculateservice.service.impl;

import com.calculateservice.entity.LandlineNumber;
import com.calculateservice.repository.LandlineNumberRepository;
import com.calculateservice.service.LandlineNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandlineNumberServiceImpl implements LandlineNumberService {

    private final LandlineNumberRepository landlineNumberRepository;

    @Override
    public List<LandlineNumber> findAllLandlineNumber() {
        return landlineNumberRepository.findAll();
    }
}
