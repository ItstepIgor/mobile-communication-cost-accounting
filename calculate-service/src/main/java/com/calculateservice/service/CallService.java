package com.calculateservice.service;


import com.calculateservice.dto.CallDto;
import com.calculateservice.entity.Call;

import java.time.LocalDate;
import java.util.List;

public interface CallService {
    void createCall(List<CallDto> calls);

    List<Call> findAllByDate(LocalDate date);

}
