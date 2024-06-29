package com.aston.aston_project.api.payment.util;

import com.aston.aston_project.api.payment.client.MockPaymentResponse;

import java.math.BigDecimal;

public interface PaymentApi {

    MockPaymentResponse tryPay(BigDecimal amount) throws PaymentException;
}
