package com.calculateservice.service.mapper;

import com.calculateservice.dto.RuleOneTimeCallServiceDTO;
import com.calculateservice.entity.RuleOneTimeCallService;
import com.calculateservice.service.OneTimeCallServiceService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {OneTimeCallServiceMapper.class,
                OneTimeCallServiceService.class})
public interface RuleOneTimeCallServiceMapper {

    @Mapping(target = "oneTimeCallServiceId", source = "oneTimeCallService.id")
    RuleOneTimeCallServiceDTO toDTO(RuleOneTimeCallService call);

    @Mapping(target = "oneTimeCallService", source = "oneTimeCallServiceId")
    RuleOneTimeCallService toEntity(RuleOneTimeCallServiceDTO callDTO);
}
