package com.calculateservice.service;

import com.calculateservice.entity.IndividualResult;

import java.time.LocalDate;
import java.util.List;

public interface IndividualResultService {

    void calcIndividualResult(LocalDate date, String number);

    void save(List<IndividualResult> individualResults);
}
