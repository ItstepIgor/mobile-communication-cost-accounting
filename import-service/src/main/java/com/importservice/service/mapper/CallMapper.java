package com.importservice.service.mapper;

import com.importservice.dto.CallDTO;
import com.importservice.entity.Call;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallMapper {

    CallDTO callToCallDto(Call call);

    Call callDtoToCall(CallDTO callDTO);
}
