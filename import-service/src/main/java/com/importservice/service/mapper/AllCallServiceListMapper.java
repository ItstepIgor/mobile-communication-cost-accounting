package com.importservice.service.mapper;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.entity.AllCallService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AllCallService.class)
public interface AllCallServiceListMapper {

    List<AllCallServiceDTO> listAllCallServiceToListAllCallServiceDto(List<AllCallService> allCallService);

    List<AllCallService> listAllCallServiceDtoToListAllCallService(List<AllCallServiceDTO> allCallServiceDTO);
}
