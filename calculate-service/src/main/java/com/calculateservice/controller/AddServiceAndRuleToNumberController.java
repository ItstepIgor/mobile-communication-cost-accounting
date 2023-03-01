package com.calculateservice.controller;

import com.calculateservice.service.AddServiceAndRuleToNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
@Tag(name = "Добавление правил и услуг к номерам")
public class AddServiceAndRuleToNumberController {

    private final AddServiceAndRuleToNumberService addServiceAndRuleToNumberService;


    @Operation(
            summary = "Добавление правил",
            description = "Добавление правил для одноразовых услуг к номерам"
    )
    @GetMapping("/rule")
    public void addRuleToNumber() {
        addServiceAndRuleToNumberService.addRuleToNumber();
    }

    @Operation(
            summary = "Добавление ежемесячных сервисов",
            description = "Добавление ежемесячных сервисов к номерам"
    )
    @GetMapping("/service")
    public void addMonthlyCallServiceToNumber() {
        addServiceAndRuleToNumberService.addMonthlyCallServiceToNumber();
    }
}
