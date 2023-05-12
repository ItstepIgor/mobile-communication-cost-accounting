package com.calculateservice.repository;


import com.calculateservice.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {

    @Query("select c from Call c  where c.invoiceDate=:date")
    List<Call> getAllCallByDate(@Param("date") LocalDate date);

    @Query(value = "select * from calls where short_number=:number", nativeQuery = true)
    List<Call> findCallByNumber(@Param("number") String number);
}
