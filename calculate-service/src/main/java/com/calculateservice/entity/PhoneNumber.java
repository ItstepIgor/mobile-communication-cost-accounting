package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"ruleOneTimeCallServices", "monthlyCallServices"})
@EqualsAndHashCode(exclude = {"ruleOneTimeCallServices" , "monthlyCallServices"})
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "number_rule_one_time_call_service", joinColumns = {@JoinColumn(name = "number_id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"number_id", "rule_id"})})
    Set<RuleOneTimeCallService> ruleOneTimeCallServices = new HashSet<>();


    //если к номеру из БД добавляем правила то нужно добавлять эти методы
    //а если будем и к правилам добавлять номера то нужно так же писать такие методы в правилах
    public void addRule(RuleOneTimeCallService ruleOneTimeCallService) {
        this.ruleOneTimeCallServices.add(ruleOneTimeCallService);
        ruleOneTimeCallService.getPhoneNumbers().add(this);
    }

    public void removeRule(RuleOneTimeCallService ruleOneTimeCallService) {
        this.ruleOneTimeCallServices.remove(ruleOneTimeCallService);
        ruleOneTimeCallService.getPhoneNumbers().remove(this);
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "number_monthly_call_service", joinColumns = {@JoinColumn(name = "number_id")},
            inverseJoinColumns = {@JoinColumn(name = "monthly_call_service_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"number_id", "monthly_call_service_id"})})
    Set<MonthlyCallService> monthlyCallServices = new HashSet<>();

    public void addMonthlyCallService(MonthlyCallService monthlyCallService) {
        this.monthlyCallServices.add(monthlyCallService);
        monthlyCallService.getPhoneNumbers().add(this);
    }

    public void removeMonthlyCallService(MonthlyCallService monthlyCallService) {
        this.monthlyCallServices.remove(monthlyCallService);
        monthlyCallService.getPhoneNumbers().remove(this);
    }
}
