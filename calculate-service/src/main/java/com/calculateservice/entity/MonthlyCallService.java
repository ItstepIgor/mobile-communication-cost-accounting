package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"monthlyCallServiceCosts"})
@Entity
public class MonthlyCallService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String monthlyCallServiceName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "monthlyCallService")
    List<MonthlyCallServiceCost> monthlyCallServiceCosts;



}
