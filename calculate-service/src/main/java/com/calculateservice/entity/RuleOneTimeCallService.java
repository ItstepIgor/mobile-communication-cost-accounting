package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//При AllArgsConstructor коллекция инициализируется null и не работает @ManyToMany
//@AllArgsConstructor
@ToString(exclude = {"groupNumbers"})
@Builder
@Entity
@Table(name = "rule_one_time_call_service")
public class RuleOneTimeCallService {
    public RuleOneTimeCallService(long id,
                                  String ruleName,
                                  OneTimeCallService oneTimeCallService,
                                  LocalTime startPayment,
                                  LocalTime endPayment,
                                  Set<GroupNumber> groupNumbers) {
        this.id = id;
        this.ruleName = ruleName;
        this.oneTimeCallService = oneTimeCallService;
        this.startPayment = startPayment;
        this.endPayment = endPayment;
        this.groupNumbers = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String ruleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_time_call_service_id", nullable = false)
    private OneTimeCallService oneTimeCallService;

    @Column(nullable = false)
    private LocalTime startPayment;

    @Column(nullable = false)
    private LocalTime endPayment;


    @ManyToMany(mappedBy = "ruleOneTimeCallServices", fetch = FetchType.LAZY)
    private Set<GroupNumber> groupNumbers = new HashSet<>();


    //методы нужны для добавления номеров к правилам из БД
//    public void addNumber(PhoneNumber phoneNumber) {
//        this.phoneNumbers.add(phoneNumber);
//        phoneNumber.getRuleOneTimeCallServices().add(this);
//    }
//
//    public void removeNumber(PhoneNumber phoneNumber) {
//        this.phoneNumbers.remove(phoneNumber);
//        phoneNumber.getRuleOneTimeCallServices().remove(this);
//    }

}
