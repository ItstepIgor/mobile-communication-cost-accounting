package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"monthlyCallServiceCosts", "phoneNumbers"})
@Entity
public class MonthlyCallService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String monthlyCallServiceName;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "monthlyCallService")
    List<MonthlyCallServiceCost> monthlyCallServiceCosts;

    @ManyToMany(mappedBy = "monthlyCallServices", fetch = FetchType.LAZY)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();


}
