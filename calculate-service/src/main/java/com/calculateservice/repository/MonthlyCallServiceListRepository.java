package com.calculateservice.repository;

import com.calculateservice.entity.MonthlyCallServiceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MonthlyCallServiceListRepository extends JpaRepository<MonthlyCallServiceList, Long> {


}
