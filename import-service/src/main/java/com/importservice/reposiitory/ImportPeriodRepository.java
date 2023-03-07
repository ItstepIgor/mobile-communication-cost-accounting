package com.importservice.reposiitory;

import com.importservice.entity.ImportPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ImportPeriodRepository extends JpaRepository<ImportPeriod, Long> {
    @Query("select distinct i.period from ImportPeriod i")
    Set<String> findPeriod();
}
