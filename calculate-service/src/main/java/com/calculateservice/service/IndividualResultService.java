package com.calculateservice.service;

import com.calculateservice.entity.IndividualResult;

import java.util.List;

public interface IndividualResultService {

    void calcIndividualResult(String number);

    void save(List<IndividualResult> individualResults);
}
