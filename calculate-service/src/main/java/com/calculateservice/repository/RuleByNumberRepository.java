package com.calculateservice.repository;

import com.calculateservice.entity.RuleByNumber;
import com.calculateservice.entity.RuleOneTimeCallService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleByNumberRepository extends JpaRepository<RuleOneTimeCallService, Long> {

    @Query(value = "SELECT ot.one_time_call_service_name AS oneTimeCallServiceName, " +
            "pn.number AS number," +
            "r.start_payment AS startPayment," +
            "r.end_payment AS endPayment " +
            "FROM rule_one_time_call_service r " +
            "LEFT JOIN group_rule_one_time_call_service gr ON r.id = gr.rule_id " +
            "LEFT JOIN group_number gn ON gn.id = gr.group_id " +
            "LEFT JOIN phone_number pn ON gn.id = pn.group_number_id " +
            "LEFT JOIN one_time_call_service ot ON r.one_time_call_service_id = ot.id " +
            "WHERE pn.number = :number", nativeQuery = true)
    List<RuleByNumber> findRuleByNumber(@Param("number") long number);
}
