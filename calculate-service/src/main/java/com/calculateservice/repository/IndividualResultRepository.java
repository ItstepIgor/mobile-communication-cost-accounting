package com.calculateservice.repository;

import com.calculateservice.entity.IndividualResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualResultRepository extends JpaRepository<IndividualResult, Long> {
}
