package com.calculateservice.service.impl;

import com.calculateservice.dto.OneTimeCallServiceDTO;
import com.calculateservice.repository.OneTimeCallServiceRepository;
import com.calculateservice.service.OneTimeCallServiceService;
import com.calculateservice.service.mapper.OneTimeCallServiceListMapper;
import com.calculateservice.service.mapper.OneTimeCallServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OneTimeCallServiceServiceImpl implements OneTimeCallServiceService {

    private final OneTimeCallServiceRepository oneTimeCallServiceRepository;

    private final OneTimeCallServiceMapper oneTimeCallServiceMapper;

    private final OneTimeCallServiceListMapper oneTimeCallServiceListMapper;

    @Override
    public OneTimeCallServiceDTO findById(long id) {
        return oneTimeCallServiceMapper.toDTO(oneTimeCallServiceRepository.findById(id).orElse(null));
    }

    @Override
    public List<OneTimeCallServiceDTO> findAll() {
        return oneTimeCallServiceListMapper.toDTOList(oneTimeCallServiceRepository.findAll());
    }
}
