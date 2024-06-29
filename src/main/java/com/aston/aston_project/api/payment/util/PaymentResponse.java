package com.aston.aston_project.api.payment.util;

import com.aston.aston_project.entity.en.PaymentTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface PaymentResponse {

    String transaction();

    LocalDate date();

    LocalTime time();

    BigDecimal amount();

    PaymentTypeEnum status();
}
