package com.calculateservice.service;


import com.calculateservice.entity.IndividualResultPojo;

import java.time.LocalDate;
import java.util.List;

public interface IndividualResultService {

    void calcIndividualResult(LocalDate date, String number);

    List<IndividualResultPojo> getIndividualResult();

}
