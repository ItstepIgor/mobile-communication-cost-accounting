package com.calculateservice.service.mapper;

import com.calculateservice.dto.CallDto;
import com.calculateservice.entity.Call;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CallMapper.class)
public interface CallListMapper {

    List<CallDto> listCallToListCallDto(List<Call> calls);

    List<Call> listCallDtoToListCall(List<CallDto> callDTOS);
}
