package com.importservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String ownerNumber;

    @Column(nullable = false)
    private String callType;

    @Column(nullable = false)
    private LocalDateTime callDateTime;

    private String code;

    @Column(nullable = false)
    private LocalTime callTime;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private BigDecimal sum;

    @Column(nullable = false)
    private long shortNumber;

    @Column(nullable = false)
    private int dayOfWeek;

}
