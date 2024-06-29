package com.aston.aston_project.api.payment.client;

import com.aston.aston_project.api.payment.util.PaymentApi;
import com.aston.aston_project.api.payment.util.PaymentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Component whose purpose is to simulate payment service logic
 * @author K.Zemlyakov
 */
@Component
@AllArgsConstructor
public class MockPaymentAPI implements PaymentApi {

    private MockPaymentResponseGenerator generator;
    @Override
    public MockPaymentResponse tryPay(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)>0) {
            return generator.generate(amount);
        }
        throw new PaymentException("amount is negative");
    }
}
