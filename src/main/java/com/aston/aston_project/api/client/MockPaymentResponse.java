package com.aston.aston_project.api.client;

import com.aston.aston_project.api.util.PaymentResponse;
import com.aston.aston_project.entity.StatusTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


public record MockPaymentResponse(String transaction, LocalDate date, LocalTime time, BigDecimal amount,
                                  StatusTransaction status) implements PaymentResponse {
}
