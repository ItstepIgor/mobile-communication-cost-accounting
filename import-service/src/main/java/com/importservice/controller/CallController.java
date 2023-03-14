package com.importservice.controller;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.service.CallService;
import com.importservice.service.AllCallServiceService;
import com.importservice.service.CallServiceMTS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calls")
@Tag(name = "Импорт данных из файла excel", description = "Импортируються данные по звонкам и всем услугам из excel в БД " +
        "и отправка при необходимости в другой сервис")
@CrossOrigin("http://localhost:8765")
public class CallController {

    private final CallService callService;
    private final CallServiceMTS callServiceMTS;
    private final AllCallServiceService allCallServiceService;

    @GetMapping("/check")
    public String check() {
        return "All is OK!!!";
    }


    @Operation(
            summary = "Импорт данных",
            description = "Вызывается метод заполнения данных по номерам телефонов и услугам"
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") @Parameter(description = "Файл excel имя файла file") MultipartFile file) {
        callService.createCall(file);
        allCallServiceService.createCallService(file);
    }

    @Operation(
            summary = "Для получения информации по всем сервисам",
            description = "Получение информации по всем сервисам из БД "
    )
    @GetMapping("/service")
    public List<AllCallServiceDTO> findAllCommonCallService(@RequestParam(value = "date")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                                                    fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                                            LocalDate date) {
        return allCallServiceService.findAllByDate(date);
    }

    @GetMapping("/importmts")
    public void importMTS() {
        callServiceMTS.createCall();
    }

}
