package com.calculateservice.service.impl;

import com.calculateservice.entity.MobileOperator;
import com.calculateservice.repository.MobileOperatorRepository;
import com.calculateservice.service.MobileOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MobileOperatorServiceImpl implements MobileOperatorService {

    private final MobileOperatorRepository mobileOperatorRepository;

    @Override
    public MobileOperator findById(long id) {
        return mobileOperatorRepository.findById(id).orElse(null);
    }
}
