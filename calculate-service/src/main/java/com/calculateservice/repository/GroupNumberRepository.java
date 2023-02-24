package com.calculateservice.repository;

import com.calculateservice.entity.GroupNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupNumberRepository extends JpaRepository <GroupNumber, Long> {
}
