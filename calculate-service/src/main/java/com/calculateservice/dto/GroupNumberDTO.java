package com.calculateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupNumberDTO {
    private long id;
    private String groupNumberName;
    private List<RuleOneTimeCallServiceDTO> ruleOneTimeCallServiceDTOs;
}
