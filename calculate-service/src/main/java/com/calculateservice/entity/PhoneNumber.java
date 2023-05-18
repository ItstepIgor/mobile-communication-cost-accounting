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
@ToString(exclude = {"monthlyCallServices", "results", "monthlyCallServiceLists"})
@EqualsAndHashCode(exclude = {"monthlyCallServices", "results", "monthlyCallServiceLists"})
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phoneNumber")
    List<IndividualResult> individualResults;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "phone_number_monthly_call_service_list", joinColumns = {@JoinColumn(name = "number_id")},
            inverseJoinColumns = {@JoinColumn(name = "monthly_call_service_list_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"number_id", "monthly_call_service_list_id"})})
    Set<MonthlyCallServiceList> monthlyCallServiceLists = new HashSet<>();

    //если к номеру из БД добавляем правила то нужно добавлять эти методы
    //а если будем и к правилам добавлять севвисам то нужно так же писать такие методы в сервисах
    public void addMonthlyCallServiceList(MonthlyCallServiceList monthlyCallServiceList) {
        this.monthlyCallServiceLists.add(monthlyCallServiceList);
        monthlyCallServiceList.getPhoneNumbers().add(this);
    }

    public void removeRule(MonthlyCallServiceList monthlyCallServiceList) {
        this.monthlyCallServiceLists.remove(monthlyCallServiceList);
        monthlyCallServiceList.getPhoneNumbers().remove(this);
    }

}
