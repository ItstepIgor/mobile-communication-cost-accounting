package com.calculateservice.service.mapper;

import com.calculateservice.dto.MonthlyCallServiceListDTO;
import com.calculateservice.entity.MonthlyCallServiceList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyCallServiceListMapper {

    MonthlyCallServiceListDTO toDTO(MonthlyCallServiceList call);

    MonthlyCallServiceList toEntity(MonthlyCallServiceListDTO callDTO);
}
