package com.calculateservice.repository;

import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.entity.PhoneNumberPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    PhoneNumber findPhoneNumberByNumber(Long number);


    List<PhoneNumber> findAllPhoneByGroupNumberId(Long id);


    @Query(value = "SELECT o.owner_name      AS owner, " +
            "p.number                        AS number, " +
            "pos.position_name               AS position " +
            "FROM phone_number p " +
            "LEFT JOIN owner o ON o.number_id = p.id " +
            "LEFT JOIN position pos ON o.position_id = pos.id " +
            "WHERE p.mobile_operator_id = :mobileOperatorId ", nativeQuery = true)
    List<PhoneNumberPojo> getListPhoneNumber(@Param("mobileOperatorId") long mobileOperatorId);
}
