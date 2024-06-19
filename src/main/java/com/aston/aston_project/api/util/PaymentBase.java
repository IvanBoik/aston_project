package com.aston.aston_project.api.util;

import com.aston.aston_project.api.client.MockPaymentResponse;
import com.aston.aston_project.entity.Order;

import java.math.BigDecimal;

public interface PaymentBase {

    MockPaymentResponse tryPay(BigDecimal amount) throws PaymentException;
}
