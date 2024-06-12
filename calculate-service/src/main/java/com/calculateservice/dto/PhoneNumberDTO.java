package com.calculateservice.dto;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDTO {

    private long id;

    private long number;

    private String creationDate;

    private long groupNumberId;

    private long mobileOperatorId;

    private List<MonthlyCallServiceListDTO> monthlyCallServiceListDTOs;

}
