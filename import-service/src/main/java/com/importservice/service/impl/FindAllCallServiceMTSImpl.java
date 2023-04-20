package com.importservice.service.impl;

import com.importservice.dto.AllExpensesByPhoneNumberDTO;
import com.importservice.reposiitory.AllExpensesByPhoneNumberRepository;
import com.importservice.service.FindAllCallServiceMTS;
import com.importservice.service.mapper.AllExpensesByPhoneNumberListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllCallServiceMTSImpl implements FindAllCallServiceMTS {

    private final AllExpensesByPhoneNumberRepository allExpensesByPhoneNumberRepository;

    private final AllExpensesByPhoneNumberListMapper allExpensesByPhoneNumberListMapper;

    @Override
    public List<AllExpensesByPhoneNumberDTO> findAllByDate(LocalDate date) {

      return   allExpensesByPhoneNumberListMapper
              .listExpensesByPhoneNumberToListExpensesByPhoneNumberDto(allExpensesByPhoneNumberRepository.getAllCallServicesByDate(date));

    }
}
