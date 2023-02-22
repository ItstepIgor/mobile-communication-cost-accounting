package com.importservice.service.mapper;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.entity.AllCallService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AllCallServiceMapper {

    AllCallServiceDTO allCallServiceToAllCallServiceDto(AllCallService allCallService);

    AllCallService allCallServiceDtoToAllCallService(AllCallServiceDTO allCallServiceDTO);
}
