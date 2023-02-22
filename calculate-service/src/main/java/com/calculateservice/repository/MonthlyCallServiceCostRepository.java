package com.calculateservice.repository;

import com.calculateservice.entity.MonthlyCallServiceCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyCallServiceCostRepository extends JpaRepository<MonthlyCallServiceCost, Long> {
}
