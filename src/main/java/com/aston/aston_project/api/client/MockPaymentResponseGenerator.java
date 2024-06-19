package com.aston.aston_project.api.client;

import com.aston.aston_project.entity.StatusTransaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockPaymentResponseGenerator{

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    public MockPaymentResponse generate(BigDecimal amount){
       return new MockPaymentResponse(
                generateTransaction(),
                getDate(),
                getTime(),
                amount,
                generateStatus()
        );
    }

    private static String generateTransaction() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 20).replace("-", "");
    }

    private static StatusTransaction generateStatus(){
        int condition = RANDOM.nextInt(2);
        if (condition == 0) {
            return StatusTransaction.DECLINED;
        }
        return StatusTransaction.PAID;
    }

    private static LocalDate getDate(){
        return LocalDate.now(ZoneId.of("UTC-0"));
    }

    private static LocalTime getTime(){
        return LocalTime.now(ZoneId.of("UTC-0"));
    }
}