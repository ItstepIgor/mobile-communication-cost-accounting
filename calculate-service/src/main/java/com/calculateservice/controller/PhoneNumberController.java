package com.calculateservice.controller;

import com.calculateservice.service.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping("/rule")
    public void addRuleToNumber (){
        phoneNumberService.addRuleToNumber();
    }

    @GetMapping("/service")
    public void addMonthlyCallServiceToNumber (){
        phoneNumberService.addMonthlyCallServiceToNumber();
    }
}
