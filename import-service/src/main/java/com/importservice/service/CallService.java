package com.importservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface CallService {


    void create(MultipartFile file);


    Set<String> findAllByCallType();
}
