package com.calculateservice.service.mapper;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = GroupNumber.class)
public interface GroupNumberListMapper {

    List<GroupNumberDTO> toListDTO(List<GroupNumber> phoneNumber);

    List<GroupNumber> toListEntity(List<GroupNumberDTO> phoneNumberDto);
}
