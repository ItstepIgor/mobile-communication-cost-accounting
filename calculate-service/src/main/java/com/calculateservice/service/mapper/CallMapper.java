package com.calculateservice.service.mapper;

import com.calculateservice.dto.CallDTO;
import com.calculateservice.entity.Call;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallMapper {

    CallDTO toDTO(Call call);

    Call toEntity(CallDTO callDTO);
}
