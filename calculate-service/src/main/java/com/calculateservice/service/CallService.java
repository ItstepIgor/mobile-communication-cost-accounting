package com.calculateservice.service;


import com.calculateservice.dto.CallDto;

import java.util.List;

public interface CallService {
    void createCall(List<CallDto> calls);

}
