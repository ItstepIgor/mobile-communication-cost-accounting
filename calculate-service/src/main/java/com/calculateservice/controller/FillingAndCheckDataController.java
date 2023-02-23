package com.calculateservice.controller;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.service.CheckDataService;
import com.calculateservice.service.FillingDataBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculate")
public class FillingAndCheckDataController {

    private final FillingDataBaseService fillingDataBaseService;
    private final CheckDataService checkDataService;

    @GetMapping("/service")
    public void getService() {
        fillingDataBaseService.fillingDataBase();
    }

//    @CrossOrigin
    @GetMapping("/check")
    public List<AllCallServiceDTO> checkSumMonthlyCallService() {
        return checkDataService.checkSumMonthlyCallService();
    }

}
