package com.importservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "call_service")
public class ImportCallService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String ownerNumber;

    @Column(nullable = false)
    private String callServiceName;

    @Column(nullable = false)
    private String callTime;

    @Column(nullable = false)
    private String vatTax;

    @Column(nullable = false)
    private long number;

    @Column(nullable = false)
    private BigDecimal sum;

    @Column(nullable = false)
    private Boolean monthly;
}
