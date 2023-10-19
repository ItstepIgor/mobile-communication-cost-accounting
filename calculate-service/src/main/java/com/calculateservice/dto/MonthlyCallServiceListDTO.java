package com.calculateservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyCallServiceListDTO {


    private long id;

    private String monthlyCallServiceName;

    private String creationDate;
}
