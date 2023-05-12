package com.calculateservice.service.impl;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.repository.PhoneNumberRepository;
import com.calculateservice.service.PhoneNumberService;
import com.calculateservice.service.mapper.PhoneNumberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    private final PhoneNumberMapper phoneNumberMapper;

    @Override
    public List<PhoneNumber> findAll() {
        return phoneNumberRepository.findAll();
    }

    @Override
    public PhoneNumber findById(Long id) {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    @Override
    public void update(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = phoneNumberMapper.toEntity(phoneNumberDTO);
        phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public PhoneNumber findPhoneNumberByNumber(Long number) {
        return phoneNumberRepository.findPhoneNumberByNumber(number);
    }
}
