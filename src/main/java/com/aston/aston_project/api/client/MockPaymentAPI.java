package com.aston.aston_project.api.client;

import com.aston.aston_project.api.util.PaymentException;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.api.util.PaymentBase;
import com.aston.aston_project.entity.ProductList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@AllArgsConstructor
public class MockPaymentAPI implements PaymentBase {

    private MockPaymentResponseGenerator generator;
    @Override
    public MockPaymentResponse tryPay(BigDecimal amount) throws PaymentException {
        if(amount.compareTo(BigDecimal.ZERO)>0) {
            return generator.generate(amount);
        }
        throw new PaymentException("amount is negative");
    }
}
