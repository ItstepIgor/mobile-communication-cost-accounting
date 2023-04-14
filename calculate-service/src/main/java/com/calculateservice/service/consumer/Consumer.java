package com.calculateservice.service.consumer;

import com.calculateservice.dto.CallDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Consumer {

    private final ObjectMapper objectMapper;
    private static final String CALL_TOPIC = "${topic.name}";

    private List<CallDto> callDtoList;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
//
    @KafkaListener(topics = CALL_TOPIC)
    public void consumeMessage(String callString) throws JsonProcessingException {
        log.info("message consumed {}", callString);
//todo переписать на прием list object
        CallDto callDto = objectMapper.readValue(callString, CallDto.class);
//        callDtoList.add(callDto);
        System.out.println(callDto);
    }
}
