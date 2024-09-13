package com.calculateservice.service.mapper;

import com.calculateservice.dto.TransferWorkDayDTO;
import com.calculateservice.entity.TransferWorkDay;
import com.calculateservice.service.TypeTransferWorkDayService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TypeTransferWorkDayService.class})
public interface TransferWorkDayMapper {

    @Mapping(target = "typeTransferWorkDayId", source = "typeTransferWorkDay.id")
    TransferWorkDayDTO toDTO(TransferWorkDay transferWorkDay);

    @Mapping(target = "typeTransferWorkDay", source = "typeTransferWorkDayId")
    TransferWorkDay toEntity(TransferWorkDayDTO transferWorkDayDTO);

}
