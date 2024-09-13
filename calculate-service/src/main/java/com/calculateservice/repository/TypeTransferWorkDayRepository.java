package com.calculateservice.repository;

import com.calculateservice.entity.TypeTransferWorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTransferWorkDayRepository extends JpaRepository<TypeTransferWorkDay, Long> {


}
