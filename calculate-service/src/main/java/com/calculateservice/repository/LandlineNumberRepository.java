package com.calculateservice.repository;

import com.calculateservice.entity.LandlineNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandlineNumberRepository extends JpaRepository <LandlineNumber, Long> {
}
