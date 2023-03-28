package com.importservice.reposiitory;

import com.importservice.entity.TariffByNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffByNumberRepository extends JpaRepository <TariffByNumber, Long> {
}
