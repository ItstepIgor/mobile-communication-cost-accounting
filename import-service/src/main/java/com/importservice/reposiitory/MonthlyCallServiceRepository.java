package com.importservice.reposiitory;

import com.importservice.entity.MonthlyCallService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyCallServiceRepository extends JpaRepository<MonthlyCallService, Long> {
}
