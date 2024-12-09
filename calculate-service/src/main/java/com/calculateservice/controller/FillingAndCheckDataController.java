package com.calculateservice.controller;

import com.calculateservice.dto.TransferWorkDayDTO;
import com.calculateservice.service.FillingDataBaseService;
import com.calculateservice.service.IndividualResultService;
import com.calculateservice.service.ResultService;
import com.calculateservice.service.TransferWorkDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculate")
@Tag(name = "Заполнение БД и окончательный расчет", description = "Заполняються данные по номерам телефонов, услугам, переносам" +
        "рабочих дней. Запуск окончательного расчета")
@CrossOrigin
public class FillingAndCheckDataController {

    private final FillingDataBaseService fillingDataBaseService;

    private final ResultService resultService;

    private final IndividualResultService individualResultService;

    private final TransferWorkDayService transferWorkDayService;

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

    @Operation(
            summary = "Получение списка рабочих и выходных дней",
            description = "Получение списка перенесенных рабочих и выходных дней"
    )
    @GetMapping("/getday")
    public List<TransferWorkDayDTO> findAllPhones() {
        return transferWorkDayService.findAll();
    }


    @Operation(
            summary = "Добавление перенесенных дней ",
            description = "Добавление перенеса рабочих или выходных дней 1 - выходные в будний день" +
                    "2 - работа в субботу или воскресенье"
    )
    @PostMapping
    public void createTransferWorkDay(@RequestBody TransferWorkDayDTO transferWorkDayDTO) {
        transferWorkDayService.save(transferWorkDayDTO);
    }

    @Operation(
            summary = "Удаление перенесенного дня",
            description = "Удаление перенесенного дня"
    )
    @DeleteMapping("/{id}")
    public void deleteTransferWorkDay(@PathVariable long id) {
        transferWorkDayService.delete(id);
    }
}
