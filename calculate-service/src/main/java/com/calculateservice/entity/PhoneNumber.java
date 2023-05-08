package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"monthlyCallServices", "results"})
@EqualsAndHashCode(exclude = {"monthlyCallServices", "results"})
@Entity
@Table(name = "phone_number")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long number;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_number_id", nullable = false)
    private GroupNumber groupNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobile_operator_id", nullable = false)
    private MobileOperator mobileOperator;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phoneNumber")
    List<MonthlyCallService> monthlyCallServices;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phoneNumber")
    List<Result> results;
}
