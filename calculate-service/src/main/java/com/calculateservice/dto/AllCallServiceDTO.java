package com.calculateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCallServiceDTO {
    private String ownerNumber;

    private String callServiceName;

    private String callTime;

    private String vatTax;

    private long number;

    private BigDecimal sum;

    private Boolean oneTimeCallService;
}
