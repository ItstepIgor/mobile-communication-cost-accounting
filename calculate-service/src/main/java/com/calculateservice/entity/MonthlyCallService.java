package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "monthly_call_service")
public class MonthlyCallService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String monthlyCallServiceName;

    @Column(nullable = false)
    private String vatTax;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @Column(nullable = false)
    private BigDecimal sum;

    @Column(name = "sum_with_nds", nullable = false)
    private BigDecimal sumWithNDS;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "number_id", nullable = false)
    private PhoneNumber phoneNumber;
}
