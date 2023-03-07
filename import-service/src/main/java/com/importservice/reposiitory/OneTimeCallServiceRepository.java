package com.importservice.reposiitory;


import com.importservice.entity.OneTimeCallService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OneTimeCallServiceRepository extends JpaRepository<OneTimeCallService, Long> {

    @Query("select distinct c.oneTimeCallServiceName from OneTimeCallService c")
    Set<String> findOneTimeCallServiceName();
}
