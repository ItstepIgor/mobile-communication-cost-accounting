package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"transferWorkDays"})
@EqualsAndHashCode(exclude = {"transferWorkDays"})
@Builder
@Entity
@Table(name = "type_transfer_work_day")
public class TypeTransferWorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String typeTransferWorkDayName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeTransferWorkDay")
    List<TransferWorkDay> transferWorkDays;
}
