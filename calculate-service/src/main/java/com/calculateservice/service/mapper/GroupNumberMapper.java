package com.calculateservice.service.mapper;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupNumberMapper {

    GroupNumberDTO toDTO(GroupNumber call);

    GroupNumber toEntity(GroupNumberDTO callDTO);
}
