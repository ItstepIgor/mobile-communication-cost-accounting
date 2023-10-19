package com.calculateservice.controller;

import com.calculateservice.dto.MonthlyCallServiceListDTO;
import com.calculateservice.dto.PhoneNumberDTO;
import com.calculateservice.service.AddMonthlyServiceToNumberService;
import com.calculateservice.service.MonthlyCallServiceListService;
import com.calculateservice.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
@Tag(name = "Редактирование номеров и добавление ежемесячных услуг к номерам")
@CrossOrigin("http://localhost:8765")
public class PhoneNumberAndMonthlyCallServiceForCalcController {

    private final AddMonthlyServiceToNumberService addMonthlyServiceToNumberService;

    private final PhoneNumberService phoneNumberService;

    private final MonthlyCallServiceListService monthlyCallServiceListService;


    @Operation(
            summary = "Добавление ежемесячных услуг",
            description = "Добавление ежемесячных услуг к номерам"
    )
    @GetMapping("/addservice")
    public void addMonthlyCallServiceToNumber(@RequestParam Long numberId, @RequestParam Long serviceId) {
        addMonthlyServiceToNumberService.addMonthlyCallServiceToNumber(numberId, serviceId);
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

    @Operation(
            summary = "Получение списка номеров",
            description = "Получение списка номеров используемых на предприятии"
    )
    @GetMapping("/getphones")
    public List<PhoneNumberDTO> findAllPhones() {
        return phoneNumberService.findAll();
    }


    @Operation(
            summary = "Получение номера по id",
            description = "Получение номера по id"
    )
    @GetMapping("/getphone")
    public PhoneNumberDTO findById(Long id) {
        return phoneNumberService.findById(id);
    }

    @Operation(
            summary = "Получение списка ежемесячных сервисов",
            description = "Получение списка ежемесячных сервисов," +
                    " которые имеют периодическую абонентскую плату "
    )
    @GetMapping("/getservices")
    public List<MonthlyCallServiceListDTO> findAllServices() {
        return monthlyCallServiceListService.findAll();
    }

}