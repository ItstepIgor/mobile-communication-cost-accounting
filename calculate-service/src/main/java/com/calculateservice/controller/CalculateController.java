package com.calculateservice.controller;

import com.calculateservice.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculate")
public class CalculateController {

    private final CalculateService calculateService;

    @GetMapping("/service")
    public void getService() {
        calculateService.createAllCallService();
    }

}
