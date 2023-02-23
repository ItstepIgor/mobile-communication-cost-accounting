package com.calculateservice.repository;

import com.calculateservice.entity.MonthlyCallServiceCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonthlyCallServiceCostRepository extends JpaRepository<MonthlyCallServiceCost, Long> {


    @Query("select mc from MonthlyCallServiceCost mc where mc.monthlyCallService.id=:id " +
            "order by mc.dateSumStart, mc.sum desc limit 1")
    MonthlyCallServiceCost findMonthlyCallServiceCosts(Long id);
}
