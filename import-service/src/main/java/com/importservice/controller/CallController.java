package com.importservice.controller;

import com.importservice.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    //TODO Удалить после настройки импорта
    @GetMapping("/uploadfromharddisk")
    public void create() {
        String file = "e:\\1\\01.2023.xlsx";
        callService.createFromFile(file);
    }
    //до это строки удалить

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
                callService.create(file);
    }
}
