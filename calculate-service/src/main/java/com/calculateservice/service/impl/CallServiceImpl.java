package com.calculateservice.service.impl;

import com.calculateservice.dto.CallDto;
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
    public void createCall(List<CallDto> calls) {
        callRepository.saveAll(callListMapper.listCallDtoToListCall(calls));
    }

    @Override
    public List<Call> findAllByDate(LocalDate date) {
        return callRepository.getAllCallByDate(date);
    }
}
