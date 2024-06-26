package com.calculateservice.service.impl;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.entity.MonthlyCallServiceList;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.entity.PhoneNumberPojo;
import com.calculateservice.repository.MonthlyCallServiceListRepository;
import com.calculateservice.repository.PhoneNumberRepository;
import com.calculateservice.service.GroupNumberService;
import com.calculateservice.service.MobileOperatorService;
import com.calculateservice.service.PhoneNumberService;
import com.calculateservice.service.mapper.PhoneNumberListMapper;
import com.calculateservice.service.mapper.PhoneNumberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    private final MonthlyCallServiceListRepository monthlyCallServiceRepository;

    private final GroupNumberService groupNumberService;

    private final MobileOperatorService mobileOperatorService;

    private final PhoneNumberMapper phoneNumberMapper;

    private final PhoneNumberListMapper phoneNumberListMapper;

    @Override
    public List<PhoneNumberDTO> findAll() {
        return phoneNumberListMapper.toListDTO(phoneNumberRepository.findAll());
    }

    @Override
    public PhoneNumberDTO findById(Long id) {
        return phoneNumberMapper.toDTO(getPhoneNumber(id));
    }

    @Override
    public void update(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = getPhoneNumber(phoneNumberDTO.getId());
        if (phoneNumberDTO.getGroupNumberId() != 0) {
            phoneNumber.setGroupNumber(groupNumberService.findGroupNumberById(phoneNumberDTO.getGroupNumberId()));
        }
        if (phoneNumberDTO.getMobileOperatorId() != 0) {
            phoneNumber.setMobileOperator(mobileOperatorService.findById(phoneNumberDTO.getMobileOperatorId()));
        }
        phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void delete(long id) {
        phoneNumberRepository.deleteById(id);
    }

    @Override
    public PhoneNumber findPhoneNumberByNumber(Long number) {
        return phoneNumberRepository.findPhoneNumberByNumber(number);
    }

    @Override
    public List<PhoneNumberDTO> findAllPhonesByGroup(Long id) {
        return phoneNumberListMapper.toListDTO(phoneNumberRepository.findAllPhoneByGroupNumberId(id));
    }

    @Override
    public List<PhoneNumberPojo> getListPhoneNumber(long mobileOperatorId) {
        return phoneNumberRepository.getListPhoneNumber(mobileOperatorId);
    }

    @Override
    public void addMonthlyCallServiceToNumber(Long numberId, Long serviceId) {

        MonthlyCallServiceList monthlyCallServiceList = getMonthlyCallService(serviceId);

        PhoneNumber phoneNumber = getPhoneNumber(numberId);

        phoneNumber.addMonthlyCallServiceList(monthlyCallServiceList);

        phoneNumberRepository.save(phoneNumber);

    }

    @Override
    public void removeMonthlyCallServiceToNumber(Long numberId, Long serviceId) {

        MonthlyCallServiceList monthlyCallServiceList = getMonthlyCallService(serviceId);

        PhoneNumber phoneNumber = getPhoneNumber(numberId);

        phoneNumber.removeMonthlyCallServiceList(monthlyCallServiceList);

        phoneNumberRepository.save(phoneNumber);

    }


    private PhoneNumber getPhoneNumber(Long id) {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    private MonthlyCallServiceList getMonthlyCallService(Long id) {
        return monthlyCallServiceRepository.findById(id).orElse(null);
    }


}
