package com.calculateservice.entity;

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
@Table(name = "result")
public class Result {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "number_id", nullable = false)
    private PhoneNumber phoneNumber;
}
