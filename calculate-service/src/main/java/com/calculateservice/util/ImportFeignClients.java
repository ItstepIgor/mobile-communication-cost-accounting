package com.calculateservice.util;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.dto.AllExpensesByPhoneNumberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "import-service")
public interface ImportFeignClients {

    @GetMapping("/calls/service")
    ResponseEntity<List<AllCallServiceDTO>> findAllCommonCallService(@RequestParam(value = "date")
                                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                                                             fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                                                     LocalDate date);

    @GetMapping("/calls/expenses")
    ResponseEntity<List<AllExpensesByPhoneNumberDTO>> findAllExpensesByPhoneNumberMTS(@RequestParam(value = "date")
                                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                                                                           fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                                                                   LocalDate date);
}
