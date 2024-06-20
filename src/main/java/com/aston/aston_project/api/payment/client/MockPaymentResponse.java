package com.aston.aston_project.api.payment.client;

import com.aston.aston_project.api.payment.util.PaymentResponse;
import com.aston.aston_project.entity.en.PaymentTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


public record MockPaymentResponse(String transaction, LocalDate date, LocalTime time, BigDecimal amount,
                                  PaymentTypeEnum status) implements PaymentResponse {
}
