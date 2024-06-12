package com.calculateservice.service;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.entity.PhoneNumberPojo;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumberDTO> findAll();

    PhoneNumberDTO findById(Long id);

    void update (PhoneNumberDTO phoneNumberDto);

    void delete (long id);

    PhoneNumber findPhoneNumberByNumber(Long number);

    List<PhoneNumberPojo> getListPhoneNumber (long mobileOperatorId);

    void addMonthlyCallServiceToNumber(Long numberId, Long serviceId);

    void removeMonthlyCallServiceToNumber(Long numberId, Long serviceId);

}
