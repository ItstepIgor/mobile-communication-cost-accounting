package com.calculateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallDto {
    private String ownerNumber;
    private String callService;
    private LocalDateTime callDateTime;
    private LocalDate invoiceDate;
    private String code;
    private String callTime;
    private String number;
    private BigDecimal sum;
    private String shortNumber;
    private int dayOfWeek;
    private String mobileOperator;
}
