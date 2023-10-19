package com.calculateservice.service.mapper;

import com.calculateservice.dto.OneTimeCallServiceDTO;
import com.calculateservice.entity.OneTimeCallService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = OneTimeCallServiceMapper.class)
public interface OneTimeCallServiceListMapper {

    List<OneTimeCallServiceDTO> toDTOList(List<OneTimeCallService> call);

    List<OneTimeCallService> toEntityList(List<OneTimeCallServiceDTO> callDTO);
}
