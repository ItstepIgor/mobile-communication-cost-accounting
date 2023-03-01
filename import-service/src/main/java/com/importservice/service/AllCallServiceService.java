package com.importservice.service;

import com.importservice.dto.AllCallServiceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AllCallServiceService {

    void createCallService(MultipartFile file);

    List<AllCallServiceDTO> findAll();
}
