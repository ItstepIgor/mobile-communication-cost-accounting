package com.importservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность затраты по номеру МТС")
public class AllExpensesByPhoneNumberDTO {

    private String number;

    private String owner;

    private LocalDate invoiceDate;

    private BigDecimal sum;

    private BigDecimal sumWithNDS;
}
