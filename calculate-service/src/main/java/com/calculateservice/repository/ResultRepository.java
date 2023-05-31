package com.calculateservice.repository;

import com.calculateservice.entity.Result;
import com.calculateservice.entity.ResultPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {


    @Query(value = "SELECT r.owner_name AS owner, " +
            "pn.number                  AS number, " +
            "r.sum                      AS sum " +
            "FROM result r " +
            "LEFT JOIN phone_number pn ON r.number_id = pn.id " +
            "WHERE pn.mobile_operator_id = :mobileOperatorId ", nativeQuery = true)
    List<ResultPojo> getResult(@Param("mobileOperatorId") long mobileOperatorId);
}
