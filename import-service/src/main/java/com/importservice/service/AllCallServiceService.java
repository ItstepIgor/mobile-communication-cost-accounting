package com.importservice.service;

import com.importservice.dto.AllCallServiceDTO;

import java.util.List;

public interface AllCallServiceService {

    void createFromFile(String file);

    List<AllCallServiceDTO> findAll();
}
