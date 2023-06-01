package com.calculateservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IndividualResultPojo {
    String getOwner();

    String getNumber();

    String getCallService();

    LocalDateTime getCallDateTime();

    String getCallToNumber();

    String getCallType();

    BigDecimal getSum();
}
