package com.calculateservice.service.mapper;

import com.calculateservice.dto.CallDto;
import com.calculateservice.entity.Call;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallMapper {

    CallDto callToCallDto(Call call);

    Call callDtoToCall(CallDto callDTO);
}
