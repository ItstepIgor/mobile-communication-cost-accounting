package com.calculateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferWorkDayDTO {

    private long id;
    private LocalDate transferDate;
    private long typeTransferWorkDayId;

}

