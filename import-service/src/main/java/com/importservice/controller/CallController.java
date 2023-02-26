package com.importservice.controller;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.service.CallService;
import com.importservice.service.AllCallServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calls")
public class CallController {

    private final CallService callService;
    private final AllCallServiceService allCallServiceService;

    @CrossOrigin
    @GetMapping("/check")
    public String check() {
        return "All is OK!!!";
    }


    //TODO Удалить после настройки импорта
    @GetMapping("/uploadfromharddisk")
    public void create() {
        String file = "e:\\1\\01.2023.xlsx";
        allCallServiceService.createFromFile(file);
    }
    //до это строки удалить

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        callService.create(file);
    }

    @CrossOrigin
    @GetMapping("/service")
    public List<AllCallServiceDTO> findAllCommonCallService() {
        return allCallServiceService.findAll();
    }
}
