package com.calculateservice.repository;

import com.calculateservice.entity.MonthlyCallService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MonthlyCallServiceRepository extends JpaRepository<MonthlyCallService, Long> {

    @Query("select mc from MonthlyCallService mc  where mc.invoiceDate=:date")
    List<MonthlyCallService> getAllCallServicesByDate(@Param("date") LocalDate date);
}
