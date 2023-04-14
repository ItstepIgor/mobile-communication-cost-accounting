package com.importservice.service.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.importservice.entity.Call;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Producer {

    @Value("${topic.name}")
    private String callTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void sendCall(List<Call> calls) throws JsonProcessingException {
        for (Call call : calls) {
            String sendCall = objectMapper.writeValueAsString(call);
            kafkaTemplate.send(callTopic, sendCall);
            log.info("message produced {}", sendCall);
        }
    }
}
