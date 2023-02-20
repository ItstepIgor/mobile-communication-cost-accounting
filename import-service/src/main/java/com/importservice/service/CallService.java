package com.importservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface CallService {

    void createFromFile(String file);
    void create(MultipartFile file);
}
