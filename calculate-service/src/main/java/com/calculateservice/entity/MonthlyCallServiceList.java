package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//При AllArgsConstructor коллекция инициализируется null и не работает @ManyToMany
//@AllArgsConstructor
@Builder
@Entity
@Table(name = "monthly_call_service_list")
public class MonthlyCallServiceList {

    public MonthlyCallServiceList(long id,
                                  String monthlyCallServiceName,
                                  LocalDateTime creationDate,
                                  Set<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.monthlyCallServiceName = monthlyCallServiceName;
        this.creationDate = creationDate;
        this.phoneNumbers = phoneNumbers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String monthlyCallServiceName;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToMany(mappedBy = "monthlyCallServiceLists", fetch = FetchType.LAZY)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

}
