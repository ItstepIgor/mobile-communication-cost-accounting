package com.calculateservice.repository;

import com.calculateservice.entity.Result;
import com.calculateservice.entity.ResultPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {


    @Query(value = "SELECT r.owner_name AS owner, " +
            "pn.number                  AS number, " +
            "ROUND(r.sum, 2)            AS sum " +
            "FROM result r " +
            "LEFT JOIN phone_number pn ON r.number_id = pn.id " +
            "WHERE pn.mobile_operator_id = :mobileOperatorId " +
            "and r.invoice_date = :localDate and r.sum > 0 ", nativeQuery = true)
    List<ResultPojo> getResult(@Param("mobileOperatorId") long mobileOperatorId, @Param("localDate") LocalDate localDate);
}
