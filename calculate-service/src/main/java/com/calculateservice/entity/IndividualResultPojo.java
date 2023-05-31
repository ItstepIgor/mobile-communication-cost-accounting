package com.calculateservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IndividualResultPojo {
    String getOwner();

    String getNumber();

    LocalDateTime getCallDateTime();

    String getCallToNumber();

    BigDecimal getSum();
}
