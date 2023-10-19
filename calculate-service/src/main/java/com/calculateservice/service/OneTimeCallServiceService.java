package com.calculateservice.service;

import com.calculateservice.dto.OneTimeCallServiceDTO;

import java.util.List;

public interface OneTimeCallServiceService {
    OneTimeCallServiceDTO findById(long id);

    List<OneTimeCallServiceDTO> findAll();
}
