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
@Table(name = "one_time_call_service")
public class OneTimeCallService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String oneTimeCallServiceName;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oneTimeCallService")
    List<RuleOneTimeCallService> ruleOneTimeCallServices;
}
