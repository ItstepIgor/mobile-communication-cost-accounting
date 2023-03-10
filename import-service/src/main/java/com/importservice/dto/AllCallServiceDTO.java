package com.importservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность сервиса")
public class AllCallServiceDTO {
    private String ownerNumber;

    @Schema(description = "Название сервиса")
    private String callServiceName;

    private String callTime;

    private String vatTax;

    private LocalDate invoiceDate;

    private long number;

    private BigDecimal sum;

    private Boolean oneTimeCallService;
}
