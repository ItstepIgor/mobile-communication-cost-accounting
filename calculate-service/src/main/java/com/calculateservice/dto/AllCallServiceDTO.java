package com.calculateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCallServiceDTO {
    private String ownerNumber;

    private String callServiceName;

    private String callTime;

    private String vatTax;

    private LocalDate invoiceDate;

    private long number;

    private BigDecimal sum;

    private BigDecimal sumWithNDS;

    private int mobileOperator;

    private Boolean oneTimeCallService;
}
