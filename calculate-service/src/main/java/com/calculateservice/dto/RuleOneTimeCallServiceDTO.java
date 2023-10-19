package com.calculateservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleOneTimeCallServiceDTO {

    private long id;

    private String ruleName;

    private long oneTimeCallServiceId;

    private String startPayment;

    private String endPayment;
}
