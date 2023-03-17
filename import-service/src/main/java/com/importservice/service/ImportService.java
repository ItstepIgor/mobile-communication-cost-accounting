package com.importservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    void importA1 (MultipartFile file);
    void importMTS (MultipartFile file);
}
