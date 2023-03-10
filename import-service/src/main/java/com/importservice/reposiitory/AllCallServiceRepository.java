package com.importservice.reposiitory;

import com.importservice.entity.AllCallService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AllCallServiceRepository extends JpaRepository <AllCallService, Long>{

    @Query("select acs from AllCallService acs  where acs.invoiceDate=:date")
    List<AllCallService> getAllCallServicesByDate(@Param("date") LocalDate date);
}
