package com.calculateservice.service.mapper;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.service.GroupNumberService;
import com.calculateservice.service.MobileOperatorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {GroupNumberService.class,
                MobileOperatorService.class,
                GroupNumberMapper.class})
public interface PhoneNumberMapper {


    @Mapping(target = "groupNumberId", source = "groupNumber.id")
    @Mapping(target = "mobileOperatorId", source = "mobileOperator.id")
    @Mapping(target = "creationDate", source = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PhoneNumberDTO toDTO(PhoneNumber phoneNumber);

    @Mapping(target = "groupNumber", source = "groupNumberId")
    @Mapping(target = "mobileOperator", source = "mobileOperatorId")
    @Mapping(target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss", defaultExpression = "java(LocalDateTime.now())")
    PhoneNumber toEntity(PhoneNumberDTO phoneNumberDto);
}