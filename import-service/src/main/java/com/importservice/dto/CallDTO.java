package com.importservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallDTO {
    private String ownerNumber;
    private String callType;
    private LocalDateTime callDateTime;
    private String code;
    private LocalTime callTime;
    private String number;
    private BigDecimal sum;
    private String shortNumber;
    private int dayOfWeek;
}
