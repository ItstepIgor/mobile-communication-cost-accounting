package com.calculateservice.service.mapper;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {RuleOneTimeCallServiceMapper.class})
public interface GroupNumberMapper {


    @Mapping(target = "ruleOneTimeCallServiceDTOs", source = "ruleOneTimeCallServices")
    GroupNumberDTO toDTO(GroupNumber call);

    @Mapping(target = "ruleOneTimeCallServices", source = "ruleOneTimeCallServiceDTOs")
    GroupNumber toEntity(GroupNumberDTO callDTO);
}
