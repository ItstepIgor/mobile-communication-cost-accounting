package com.calculateservice.repository;

import com.calculateservice.entity.MonthlyCallService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyCallServiceRepository extends JpaRepository<MonthlyCallService, Long> {
}
