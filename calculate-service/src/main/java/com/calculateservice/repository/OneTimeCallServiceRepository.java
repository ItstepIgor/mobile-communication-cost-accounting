package com.calculateservice.repository;

import com.calculateservice.entity.OneTimeCallService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeCallServiceRepository extends JpaRepository<OneTimeCallService, Long> {
}
