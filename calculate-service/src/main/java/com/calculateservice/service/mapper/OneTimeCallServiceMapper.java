package com.calculateservice.service.mapper;

import com.calculateservice.dto.OneTimeCallServiceDTO;
import com.calculateservice.entity.OneTimeCallService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OneTimeCallServiceMapper {

    OneTimeCallServiceDTO toDTO(OneTimeCallService call);

    OneTimeCallService toEntity(OneTimeCallServiceDTO callDTO);
}
