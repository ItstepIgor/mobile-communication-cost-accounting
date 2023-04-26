package com.calculateservice.service.impl;

import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.repository.PhoneNumberRepository;
import com.calculateservice.service.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public List<PhoneNumber> findAll() {
        return phoneNumberRepository.findAll();
    }
}
