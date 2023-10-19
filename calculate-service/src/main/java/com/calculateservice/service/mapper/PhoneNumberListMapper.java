package com.calculateservice.service.mapper;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.entity.PhoneNumber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PhoneNumber.class, PhoneNumberMapper.class})
public interface PhoneNumberListMapper {

    List<PhoneNumberDTO> toListDTO(List<PhoneNumber> phoneNumber);

    List<PhoneNumber> toListEntity(List<PhoneNumberDTO> phoneNumberDto);
}
