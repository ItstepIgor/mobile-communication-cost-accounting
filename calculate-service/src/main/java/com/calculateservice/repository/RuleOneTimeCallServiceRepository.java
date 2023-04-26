package com.calculateservice.repository;

import com.calculateservice.entity.RuleOneTimeCallService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleOneTimeCallServiceRepository extends JpaRepository<RuleOneTimeCallService, Long> {
}
