package com.importservice.controller;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.dto.AllExpensesByPhoneNumberDTO;
import com.importservice.service.AllCallServiceService;
import com.importservice.service.FindAllInformationOnMTS;
import com.importservice.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calls")
@Tag(name = "Импорт данных из файла excel", description = "Импортируються данные по звонкам и всем услугам из excel в БД " +
        "и отправка при необходимости в другой сервис")
@CrossOrigin
public class CallController {

    private final ImportService importService;
    private final AllCallServiceService allCallServiceService;
    private final FindAllInformationOnMTS findAllInformationOnMTS;

    @GetMapping("/check")
    public String check() {
        return "All is OK!!!";
    }


    @Operation(
            summary = "Импорт данных по A1 из одного файла",
            description = "Используем этот метод если у нас один файл ZIP архива"
    )
    @PostMapping(value = "/importa1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadZipFile(@RequestParam("file") @Parameter(description = "Загружаем файл ZIP") MultipartFile file) {
        importService.importA1(file);
    }

    @Operation(
            summary = "Импорт данных по МТС из одного файла",
            description = "Используем этот метод если у нас один файл RAR архива"
    )
    @PostMapping(value = "/importmts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importMTS(@RequestParam("file") @Parameter(description = "Загружаем файл RAR") MultipartFile file) {
        importService.importMTS(file);
    }

    @Operation(
            summary = "Импорт данных по МТС из нескольких файлов",
            description = "Метод для загрузки на сервер и записи данных в БД из нескольких файлов RAR от МТС",
            requestBody = @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    )
    @PostMapping(value = "/multiimportmts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void multiImportMTS(@Parameter(description = "Добавляем файлы RAR по очереди")
                                   @org.springframework.web.bind.annotation.RequestBody MultipartFile[] files) {
        importService.multiImportMTS(files);
    }


//    //Методы использовались когда не работала одновременная загрузка из нескольких файлов
//    @Operation(
//            summary = "Метод только для загрузки нескольких RAR файлов МТС на сервер",
//            description = "Используем этот метод если у нас несколько файлов загружаем их по очереди"
//    )
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void upload(@RequestParam("file") @Parameter(description = "Загружаем файл RAR") MultipartFile file) {
//        importService.saveToFile(file);
//    }
//
//
//    @Operation(
//            summary = "Метод для записи в БД данных после загрузки нескольких RAR от МТС",
//            description = "Используем этот метод толоко после загрузки нескольких файлов по очереди методом /upload"
//    )
//    @PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void extract(@RequestParam("file") @Parameter(description = "Выбираем файл *.part01.rar") MultipartFile file) {
//        importService.extract(file.getOriginalFilename());
//    }

    @Operation(
            summary = "Для получения информации по всем сервисам А1",
            description = "Получение информации по всем сервисам A1 из БД "
    )
    @GetMapping("/service")
    public List<AllCallServiceDTO> findAllCommonCallService(@RequestParam(value = "date")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                                                    fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                                            @Parameter(description = "Параметр даты: dd/MM/yy, dd.MM.yyyy, dd-MM-yyyy")
                                                            LocalDate date) {
        return allCallServiceService.findAllByDate(date);
    }


    @Operation(
            summary = "Для получения информации по расходам па номерам МТС",
            description = "Получение информации по расходам па номерам МТС из БД "
    )
    @GetMapping("/expensesmts")
    public List<AllExpensesByPhoneNumberDTO> findAllExpensesByPhoneNumberMTS(@RequestParam(value = "date")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                                                                     fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                                                             @Parameter(description = "Параметр даты: dd/MM/yy, dd.MM.yyyy, dd-MM-yyyy")
                                                                             LocalDate date) {
        return findAllInformationOnMTS.findAllByDate(date);
    }

}
