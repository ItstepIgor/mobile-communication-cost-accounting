package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"ruleOneTimeCallServices"})
@Builder
@Entity
public class OneTimeCallService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String oneTimeCallServiceName;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oneTimeCallService")
    List<RuleOneTimeCallService> ruleOneTimeCallServices;
}
