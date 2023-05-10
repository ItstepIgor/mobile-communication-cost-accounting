package com.calculateservice.entity;

import java.time.LocalTime;

public interface RuleByNumber {
    String getOneTimeCallServiceName();
    long getNumber();
    LocalTime getStartPayment();
    LocalTime getEndPayment();
}
