package com.calculateservice.service.impl;

import com.calculateservice.entity.*;
import com.calculateservice.repository.*;
import com.calculateservice.service.AddMonthlyServiceToNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddMonthlyServiceToNumberServiceImpl implements AddMonthlyServiceToNumberService {

    private final MonthlyCallServiceListRepository monthlyCallServiceRepository;

    private final PhoneNumberRepository phoneNumberRepository;



    @Override
    public void addMonthlyCallServiceToNumber(Long numberId, Long serviceId) {

        MonthlyCallServiceList monthlyCallServiceList = getMonthlyCallService(serviceId);

        PhoneNumber phoneNumber = getPhoneNumber(numberId);

        phoneNumber.addMonthlyCallServiceList(monthlyCallServiceList);

        phoneNumberRepository.save(phoneNumber);

    }


    private PhoneNumber getPhoneNumber(Long id) {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    private MonthlyCallServiceList getMonthlyCallService(Long id) {
        return monthlyCallServiceRepository.findById(id).orElse(null);
    }


}

