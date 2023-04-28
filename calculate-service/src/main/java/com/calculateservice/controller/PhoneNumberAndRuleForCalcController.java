package com.calculateservice.controller;

import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.service.AddServiceAndRuleToNumberService;
import com.calculateservice.service.PhoneNumberService;
import com.calculateservice.service.mapper.PhoneNumberMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
@Tag(name = "Редактирование номеров и добавление правил и услуг к номерам")
@CrossOrigin("http://localhost:8765")
public class PhoneNumberAndRuleForCalcController {

    private final AddServiceAndRuleToNumberService addServiceAndRuleToNumberService;

    private final PhoneNumberService phoneNumberService;

    private final PhoneNumberMapper phoneNumberMapper;

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

    @Operation(
            summary = "Редактирование груп в номерах телефонов",
            description = "Изменнение груп привязанных к номерам телефонов," +
                    " Если не указать дату и время, то береться текущее "
    )
    @PutMapping("/update")
    public void updatePhoneNumber(@RequestBody PhoneNumberDTO phoneNumberDto) {
        phoneNumberService.update(phoneNumberDto);
    }

    @GetMapping("/get")
    public PhoneNumberDTO findById(long id) {
        return phoneNumberMapper.toDTO(phoneNumberService.findById(id));
    }

}
