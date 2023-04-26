package com.calculateservice.repository;

import com.calculateservice.entity.MobileOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileOperatorRepository extends JpaRepository <MobileOperator, Long> {
}
