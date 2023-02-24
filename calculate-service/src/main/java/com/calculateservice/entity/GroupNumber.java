package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"phoneNumbers"})
@Entity
public class GroupNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String groupNumberName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupNumber")
    List<PhoneNumber> phoneNumbers;
}
