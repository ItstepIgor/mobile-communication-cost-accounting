package com.calculateservice.service.impl;

import com.calculateservice.dto.CallDTO;
import com.calculateservice.entity.Call;
import com.calculateservice.repository.CallRepository;
import com.calculateservice.service.CallService;
import com.calculateservice.service.mapper.CallListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CallServiceImpl implements CallService {

    private final CallRepository callRepository;

    private final CallListMapper callListMapper;

    @Override
    public void createCall(List<CallDTO> calls) {
        callRepository.saveAll(callListMapper.toListEntity(calls));
    }

    @Override
    public List<Call> findAllByDate(LocalDate date) {
        return callRepository.getAllCallByDate(date);
    }

    @Override
    public List<Call> findAllCallByNumber(String number) {
        return callRepository.findCallByNumber(number);
    }
}
