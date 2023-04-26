package com.importservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String ownerNumber;

    @Column(nullable = false)
    private String callService;

    @Column(nullable = false)
    private LocalDateTime callDateTime;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    private String code;

    @Column(nullable = false)
    private String callTime;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private BigDecimal sum;

    @Column(nullable = false)
    private long shortNumber;

    @Column(nullable = false)
    private int dayOfWeek;

    @Column(nullable = false)
    private int mobileOperator;
}
