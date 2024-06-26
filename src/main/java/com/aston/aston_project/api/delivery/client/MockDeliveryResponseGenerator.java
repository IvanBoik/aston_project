package com.aston.aston_project.api.delivery.client;

import com.aston.aston_project.api.delivery.util.DeliveryStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockDeliveryResponseGenerator {
    private static ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final String[] NAME_ARRAY = new String[]{
            "Кирилл","Иван","Егор","Данил","Игорь","Мария"
    };

    public UUID generateUUID(){
        return UUID.randomUUID();
    }

    public String generateName(){
        return NAME_ARRAY[RANDOM.nextInt(NAME_ARRAY.length)];
    }

    public String generatePhone(){
        return String.valueOf(RANDOM.nextLong(7_900_000_00_00L,7_999_999_99_99L));
    }

    public DeliveryStatus generateDeliveryStatus(){
        int random = RANDOM.nextInt(100);
        if(random < 10) return DeliveryStatus.DECLINED;
        return DeliveryStatus.ON_THE_WAY;
    }

}
