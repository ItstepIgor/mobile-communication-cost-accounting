package com.importservice.service.mapper;

import com.importservice.dto.CallDTO;
import com.importservice.entity.Call;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CallMapper.class)
public interface CallListMapper {

    List<CallDTO> listCallToListCallDto(List<Call> calls);

    List<Call> listCallDtoToListCall(List<CallDTO> callDTOS);
}
