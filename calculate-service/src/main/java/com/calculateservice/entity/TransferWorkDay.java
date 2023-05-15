package com.calculateservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transfer_work_day")
public class TransferWorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate transferDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_transfer_work_day_id", nullable = false)
    private TypeTransferWorkDay typeTransferWorkDay;

}

