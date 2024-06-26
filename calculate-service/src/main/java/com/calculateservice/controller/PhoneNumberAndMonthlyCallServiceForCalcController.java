package com.calculateservice.controller;

import com.calculateservice.dto.MonthlyCallServiceListDTO;
import com.calculateservice.dto.PhoneNumberDTO;
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

    private final PhoneNumberService phoneNumberService;

    private final MonthlyCallServiceListService monthlyCallServiceListService;


    @Operation(
            summary = "Добавление ежемесячных услуг",
            description = "Добавление ежемесячных услуг к номерам (за которые оплачиваюет предприятие)"
    )
    @GetMapping("/addservice")
    public void addMonthlyCallServiceToNumber(@RequestParam Long numberId, @RequestParam Long serviceId) {
        phoneNumberService.addMonthlyCallServiceToNumber(numberId, serviceId);
    }

    @Operation(
            summary = "Удаление ежемесячных услуг",
            description = "Удаление ежемесячных услуг у номерам (за которые оплачиваюет предприятие)"
    )
    @GetMapping("/removeservice")
    public void removeMonthlyCallServiceToNumber(@RequestParam Long numberId, @RequestParam Long serviceId) {
        phoneNumberService.removeMonthlyCallServiceToNumber(numberId, serviceId);
    }

    @Operation(
            summary = "Редактирование групп и оператора для номеров телефонов",
            description = "Изменнение груп привязанных к номерам телефонов и оператора" +
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
            summary = "Получение списка ежемесячных услуг",
            description = "Получение списка ежемесячных услуг," +
                    " которые имеют периодическую абонентскую плату "
    )
    @GetMapping("/getservices")
    public List<MonthlyCallServiceListDTO> findAllServices() {
        return monthlyCallServiceListService.findAll();
    }

    @Operation(
            summary = "Получение списка номеров по группе",
            description = "Получение списка номеров определенной группы"
    )
    @GetMapping("/getphonesbygroup")
    public List<PhoneNumberDTO> findAllPhonesByGroup(Long id) {
        return phoneNumberService.findAllPhonesByGroup(id);
    }

    @Operation(
            summary = "Удаление номеров ",
            description = "Удаление номеров"
    )
    @DeleteMapping("/{id}")
    public void deleteGroupNumber(@PathVariable long id) {
        phoneNumberService.delete(id);
    }

}
