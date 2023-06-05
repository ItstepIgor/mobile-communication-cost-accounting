package com.calculateservice.service;

import com.calculateservice.entity.ResultPojo;

import java.time.LocalDate;
import java.util.List;

public interface ResultService {

    void calcResult(LocalDate date);

    List<ResultPojo> getResult(long mobileOperatorId, LocalDate localDate);
}
