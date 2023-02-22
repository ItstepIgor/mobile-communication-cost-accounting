package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
