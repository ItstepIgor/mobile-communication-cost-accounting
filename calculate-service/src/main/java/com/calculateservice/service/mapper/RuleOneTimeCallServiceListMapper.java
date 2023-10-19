package com.calculateservice.service.mapper;

import com.calculateservice.dto.RuleOneTimeCallServiceDTO;
import com.calculateservice.entity.RuleOneTimeCallService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RuleOneTimeCallServiceMapper.class)
public interface RuleOneTimeCallServiceListMapper {

    List<RuleOneTimeCallServiceDTO> toDTOList(List<RuleOneTimeCallService> call);

    List<RuleOneTimeCallService> toEntityList(List<RuleOneTimeCallServiceDTO> callDTO);
}
