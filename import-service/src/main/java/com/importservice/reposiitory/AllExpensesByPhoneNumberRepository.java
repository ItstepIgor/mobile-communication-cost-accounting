package com.importservice.reposiitory;

import com.importservice.entity.AllExpensesByPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AllExpensesByPhoneNumberRepository extends JpaRepository<AllExpensesByPhoneNumber, Long> {

    @Query("select a from AllExpensesByPhoneNumber a  where a.invoiceDate=:date")
    List<AllExpensesByPhoneNumber> getAllCallServicesByDate(@Param("date") LocalDate date);
}
