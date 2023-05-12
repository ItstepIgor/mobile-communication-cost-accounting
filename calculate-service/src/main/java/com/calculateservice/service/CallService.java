package com.calculateservice.service;


import com.calculateservice.dto.CallDTO;
import com.calculateservice.entity.Call;

import java.time.LocalDate;
import java.util.List;

public interface CallService {
    void createCall(List<CallDTO> calls);

    List<Call> findAllByDate(LocalDate date);

    List<Call> findAllCallByNumber(String number);

}
