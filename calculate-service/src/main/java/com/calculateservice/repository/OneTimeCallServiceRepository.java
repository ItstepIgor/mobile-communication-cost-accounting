package com.calculateservice.repository;

import com.calculateservice.entity.OneTimeCallService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimeCallServiceRepository extends JpaRepository<OneTimeCallService, Long> {
}
