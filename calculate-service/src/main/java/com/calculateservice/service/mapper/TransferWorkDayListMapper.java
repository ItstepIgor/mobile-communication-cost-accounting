package com.calculateservice.service.mapper;

import com.calculateservice.dto.TransferWorkDayDTO;
import com.calculateservice.entity.TransferWorkDay;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TransferWorkDayMapper.class)
public interface TransferWorkDayListMapper {

    List<TransferWorkDayDTO> toDTOList(List<TransferWorkDay> transferWorkDays);

}
