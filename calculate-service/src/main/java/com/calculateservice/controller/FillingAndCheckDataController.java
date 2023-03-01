package com.calculateservice.controller;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.service.CheckDataService;
import com.calculateservice.service.FillingDataBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculate")
@Tag(name = "Заполнение БД и проверка данных", description = "Заполняються и проверяються на корректность данные по номерам телефонов и услугам")
public class FillingAndCheckDataController {

    private final FillingDataBaseService fillingDataBaseService;
    private final CheckDataService checkDataService;

    //    @CrossOrigin

    @Operation(
            summary = "Заполнение БД",
            description = "Вызывается метод заполнения данных по номерам телефонов и услугам"
    )
    @GetMapping("/service")
    public void getService() {
        fillingDataBaseService.fillingDataBase();
    }

    @Operation(
            summary = "Проверка стоимости ежемесячных сервисов",
            description = "Вызывается метод сверяющий а/п ежемесячных сервисов с текущей и возвращает список отличающихся а/п "
    )
    @GetMapping("/check")
    public List<AllCallServiceDTO> checkSumMonthlyCallService() {
        return checkDataService.checkSumMonthlyCallService();
    }

}
