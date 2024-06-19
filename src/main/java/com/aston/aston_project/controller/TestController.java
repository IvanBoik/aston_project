package com.aston.aston_project.controller;

import com.aston.aston_project.api.payment.client.MockPaymentResponseGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/test")
public class TestController {

    private MockPaymentResponseGenerator generator;

    public TestController(MockPaymentResponseGenerator generator) {
        this.generator = generator;
    }

    @GetMapping
    public void test(){
        generator.generate(new BigDecimal(100));
    }
}
