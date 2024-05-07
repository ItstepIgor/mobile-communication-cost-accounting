package com.calculateservice.controller;

import com.calculateservice.service.FillingDataBaseService;
import com.calculateservice.service.IndividualResultService;
import com.calculateservice.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculate")
@Tag(name = "Заполнение БД и проверка данных", description = "Заполняються и проверяються на корректность данные по номерам телефонов и услугам")
@CrossOrigin("http://localhost:8765")
public class FillingAndCheckDataController {

    private final FillingDataBaseService fillingDataBaseService;

    private final ResultService resultService;

    private final IndividualResultService individualResultService;

    @Operation(
            summary = "При расчете вызываем этот метод первым Заполнение БД",
            description = "Вызывается метод заполнения данных по номерам телефонов и услугам указываем дату "
    )
    @GetMapping("/service")
    public void getService(@RequestParam(value = "date")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                   fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                           @Parameter(description = "Параметр даты: dd/MM/yy, dd.MM.yyyy, dd-MM-yyyy")
                           LocalDate date) {
        fillingDataBaseService.fillingDataBase(date);
    }

    @Operation(
            summary = "При расчете вызываем этот метод  после заполнения данных Провести итоговый расчет",
            description = "Вызывается метод и выполняется расчет удержания по всем номерам телефонов с указанием даты"
    )
    @GetMapping("/calc")
    public void calculateResult(@RequestParam(value = "date")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                        fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                @Parameter(description = "Параметр даты: dd/MM/yy, dd.MM.yyyy, dd-MM-yyyy")
                                LocalDate date) {
        resultService.calcResult(date);
    }

    @Operation(
            summary = "Провести итоговый расчет по одному номеру",
            description = "Вызывается метод и выполняется расчет удержания по одному номеру телефона"
    )
    @GetMapping("/calcindividul")
    public void calculateIndividualResult(@RequestParam(value = "date")
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                                      fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                              @Parameter(description = "Параметр даты: dd/MM/yy, dd.MM.yyyy, dd-MM-yyyy")
                                              LocalDate date, @RequestParam String number) {
        individualResultService.calcIndividualResult(date, number);
    }
}
