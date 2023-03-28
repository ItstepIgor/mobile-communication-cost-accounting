package com.importservice.reposiitory;

import com.importservice.entity.AllExpensesByPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllExpensesByPhoneNumberRepository extends JpaRepository<AllExpensesByPhoneNumber, Long> {
}
