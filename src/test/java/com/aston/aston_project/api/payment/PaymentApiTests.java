package com.aston.aston_project.api.payment;

import com.aston.aston_project.api.payment.client.MockPaymentAPI;
import com.aston.aston_project.api.payment.client.MockPaymentResponse;
import com.aston.aston_project.api.payment.client.MockPaymentResponseGenerator;
import com.aston.aston_project.api.payment.util.PaymentException;
import com.aston.aston_project.entity.StatusTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.RetryingTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PaymentApiTests {

    @Spy
    private MockPaymentResponseGenerator generator;

    @InjectMocks
    private MockPaymentAPI paymentAPI;


    @RetryingTest(5)
    public void payment_api_returns_paid_status() throws PaymentException {
        MockPaymentResponse response = paymentAPI.tryPay(new BigDecimal(1000));
        assertThat(response.status()).isEqualTo(StatusTransaction.PAID);
    }

    @RetryingTest(20)
    public void payment_api_returns_declined_status() throws PaymentException {
        MockPaymentResponse response = paymentAPI.tryPay(new BigDecimal(1000));
        assertThat(response.status()).isEqualTo(StatusTransaction.DECLINED);
    }

    @Test
    public void payment_api_returns_not_null_values() throws PaymentException {
        MockPaymentResponse response = paymentAPI.tryPay(new BigDecimal(1000));
        assertNotNull(response.status());
        assertNotNull(response.amount());
        assertNotNull(response.date());
        assertNotNull(response.time());
        assertNotNull(response.transaction());
    }

    @Test
    public void payment_api_throws_exception() {
        assertThrows(PaymentException.class,()->paymentAPI.tryPay(new BigDecimal(-100)));
    }


}
