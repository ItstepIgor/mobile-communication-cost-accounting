package com.importservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface CallService {


    void createCall(MultipartFile file);


    Set<String> findAllByCallService();
}
