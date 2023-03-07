package com.importservice.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TemplateResponseException {
    private final LocalDateTime timestamp;

    private final Integer status;

    private final String error;

    private final String message;

    public TemplateResponseException(Integer status, String error, String message) {
        super();
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
