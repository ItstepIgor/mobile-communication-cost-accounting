package com.calculateservice.service;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.entity.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumber> findAll();
    PhoneNumber findById(Long id);

    void update (PhoneNumberDTO phoneNumberDto);

}
