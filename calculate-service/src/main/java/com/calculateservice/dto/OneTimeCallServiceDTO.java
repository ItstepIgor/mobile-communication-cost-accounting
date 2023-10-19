package com.calculateservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneTimeCallServiceDTO {

    private long id;

    private String oneTimeCallServiceName;

    private String creationDate;

}
