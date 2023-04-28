package com.calculateservice.service.mapper;

import com.calculateservice.dto.CallDTO;
import com.calculateservice.entity.Call;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CallMapper.class)
public interface CallListMapper {

    List<CallDTO> toListDto(List<Call> calls);

    List<Call> toListEntity(List<CallDTO> callDTOS);
}
