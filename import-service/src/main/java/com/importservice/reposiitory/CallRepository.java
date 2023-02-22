package com.importservice.reposiitory;

import com.importservice.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {

    @Query ("select distinct c.callType from Call c")
    Set<String> findAllByCallType();
}
