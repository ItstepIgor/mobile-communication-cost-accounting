package com.calculateservice.entity;

import java.time.LocalTime;

public interface RuleOneTimeService {
    String getOneTimeCallServiceName();
    long getNumber();
    LocalTime getStartPayment();
    LocalTime getEndPayment();
}
