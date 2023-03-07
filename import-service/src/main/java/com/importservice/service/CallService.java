package com.importservice.service;

import org.springframework.web.multipart.MultipartFile;


public interface CallService {


    void createCall(MultipartFile file);
}
