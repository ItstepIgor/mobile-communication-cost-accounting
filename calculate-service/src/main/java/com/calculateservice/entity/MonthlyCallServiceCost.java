package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MonthlyCallServiceCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_call_service_id", nullable = false)
    private MonthlyCallService monthlyCallService;

    @Column(nullable = false)
    private LocalDate dateSumStart;

    @Column(nullable = false)
    private BigDecimal sum;

}
