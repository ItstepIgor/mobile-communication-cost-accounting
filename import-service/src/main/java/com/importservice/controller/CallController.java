package com.importservice.controller;

import com.importservice.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calls")
public class CallController {

    private final CallService callService;

    @CrossOrigin
    @GetMapping("/check")
    public String check() {
        return "All is OK!!!";
    }

    @GetMapping("/upload")
    public void create() {
        String file = "e:\\1\\01.2023.xlsx";
        callService.readFromExcel(file);
    }

}
