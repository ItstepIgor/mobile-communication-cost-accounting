package com.calculateservice.service.consumer;

import com.calculateservice.dto.CallDto;
import com.calculateservice.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final CallService callService;

    @KafkaListener(topics = "callA1", groupId = "default", containerFactory = "callListener")
    void listenerA1(List<CallDto> data) {
        callService.createCall(data);
    }

    @KafkaListener(topics = "callMTS", groupId = "default", containerFactory = "callListener")
    void listenerMTS(List<CallDto> data) {
        callService.createCall(data);
    }
}
