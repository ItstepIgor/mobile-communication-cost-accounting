package com.calculateservice.repository;

import com.calculateservice.entity.GroupNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupNumberRepository extends JpaRepository <GroupNumber, Long> {
}
