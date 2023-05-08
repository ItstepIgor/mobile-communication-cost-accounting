package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"ruleOneTimeCallServices", "phoneNumbers"})
@EqualsAndHashCode(exclude = {"ruleOneTimeCallServices" , "phoneNumbers"})
@Entity
public class GroupNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String groupNumberName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupNumber")
    List<PhoneNumber> phoneNumbers;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_rule_one_time_call_service", joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "rule_id"})})
    Set<RuleOneTimeCallService> ruleOneTimeCallServices = new HashSet<>();


    //если к номеру из БД добавляем правила то нужно добавлять эти методы
    //а если будем и к правилам добавлять номера то нужно так же писать такие методы в правилах
    public void addRule(RuleOneTimeCallService ruleOneTimeCallService) {
        this.ruleOneTimeCallServices.add(ruleOneTimeCallService);
        ruleOneTimeCallService.getGroupNumbers().add(this);
    }

    public void removeRule(RuleOneTimeCallService ruleOneTimeCallService) {
        this.ruleOneTimeCallServices.remove(ruleOneTimeCallService);
        ruleOneTimeCallService.getGroupNumbers().remove(this);
    }
}
