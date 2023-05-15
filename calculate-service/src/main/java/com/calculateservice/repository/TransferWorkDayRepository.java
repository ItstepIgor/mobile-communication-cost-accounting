package com.calculateservice.repository;

import com.calculateservice.entity.TransferWorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferWorkDayRepository extends JpaRepository<TransferWorkDay, Long> {

    @Query (value = "select *  from transfer_work_day where EXTRACT(YEAR FROM transfer_date) = :year " +
            "AND EXTRACT(MONTH FROM transfer_date) = :month", nativeQuery = true)
    public List<TransferWorkDay> findAllTransferWorkDayByDate (int year, int month);
}
