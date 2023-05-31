package com.calculateservice.repository;

import com.calculateservice.entity.IndividualResult;
import com.calculateservice.entity.IndividualResultPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualResultRepository extends JpaRepository<IndividualResult, Long> {


    @Query(value = "SELECT r.owner_name     AS owner, " +
            "pn.number                      AS number, " +
            "r.call_date_time               AS callDateTime, " +
            "r.call_to_number               AS callToNumber, " +
            "r.sum                          AS sum " +
            "FROM individual_result r " +
            "LEFT JOIN phone_number pn ON r.number_id = pn.id ", nativeQuery = true)
    List<IndividualResultPojo> getIndividualResult();
}
