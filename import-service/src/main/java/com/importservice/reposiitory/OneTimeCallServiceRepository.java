package com.importservice.reposiitory;


import com.importservice.entity.OneTimeCallService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeCallServiceRepository extends JpaRepository<OneTimeCallService, Long> {
}
