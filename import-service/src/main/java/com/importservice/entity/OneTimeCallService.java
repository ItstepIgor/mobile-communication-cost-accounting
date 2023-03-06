package com.importservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public OneTimeCallService(String oneTimeCallServiceName) {
        this.oneTimeCallServiceName = oneTimeCallServiceName;
        this.creationDate = LocalDateTime.now();
    }
}
