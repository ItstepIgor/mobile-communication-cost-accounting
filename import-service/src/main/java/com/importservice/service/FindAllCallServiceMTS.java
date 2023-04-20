package com.importservice.service;

import com.importservice.dto.AllExpensesByPhoneNumberDTO;

import java.time.LocalDate;
import java.util.List;

public interface FindAllCallServiceMTS {

    List<AllExpensesByPhoneNumberDTO> findAllByDate(LocalDate date);
}
