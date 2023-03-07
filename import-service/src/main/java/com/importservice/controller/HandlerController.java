package com.importservice.controller;


import com.importservice.exception.ImportException;
import com.importservice.exception.TemplateResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(ImportException.class)
    public ResponseEntity<TemplateResponseException> handleMyException(ImportException exception) {
        return getResponseEntity(exception.getClass().getName(), exception.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<TemplateResponseException> getResponseEntity(String error, String message, HttpStatus status) {
        System.out.println(message);
        return new ResponseEntity<>(new TemplateResponseException(status.value(), error, message), status);
    }
}
