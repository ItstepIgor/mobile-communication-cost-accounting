package com.calculateservice.service.mapper;

import com.calculateservice.dto.MonthlyCallServiceListDTO;
import com.calculateservice.entity.MonthlyCallServiceList;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MonthlyCallServiceList.class)
public interface MonthlyCallServiceListsMapper {

    List<MonthlyCallServiceListDTO> toListDTO(List<MonthlyCallServiceList> call);

    List<MonthlyCallServiceList> toListEntity(List<MonthlyCallServiceListDTO> callDTO);

}
