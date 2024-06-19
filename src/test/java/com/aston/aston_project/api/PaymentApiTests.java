package com.aston.aston_project.api;

import com.aston.aston_project.api.client.MockPaymentAPI;
import com.aston.aston_project.api.client.MockPaymentResponse;
import com.aston.aston_project.api.client.MockPaymentResponseGenerator;
import com.aston.aston_project.api.util.PaymentException;
import com.aston.aston_project.entity.StatusTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentApiTests {

    private MockPaymentAPI mockPaymentAPI;

    @BeforeEach
    public void init(){
        mockPaymentAPI = new MockPaymentAPI(new MockPaymentResponseGenerator());
    }

    @RetryingTest(5)
    public void payment_api_returns_paid_status() throws PaymentException {
        MockPaymentResponse response = mockPaymentAPI.tryPay(new BigDecimal(1000));
        assertThat(response.status()).isEqualTo(StatusTransaction.PAID);
    }

    @Test
    public void payment_api_returns_not_null_values() throws PaymentException {
        MockPaymentResponse response = mockPaymentAPI.tryPay(new BigDecimal(1000));
        assertNotNull(response.status());
        assertNotNull(response.amount());
        assertNotNull(response.date());
        assertNotNull(response.time());
        assertNotNull(response.transaction());
    }

    @Test
    public void payment_api_throws_exception() {
        assertThrows(PaymentException.class,()->mockPaymentAPI.tryPay(new BigDecimal(-100)));
    }


}
