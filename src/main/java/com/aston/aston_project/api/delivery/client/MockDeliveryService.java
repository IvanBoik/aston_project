package com.aston.aston_project.api.delivery.client;

import com.aston.aston_project.api.delivery.util.DeliveryRequest;
import com.aston.aston_project.api.delivery.util.DeliveryResponse;
import com.aston.aston_project.api.delivery.util.DeliveryService;
import com.aston.aston_project.api.delivery.util.DeliveryStatus;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;


@Data
@Component
public class MockDeliveryService implements DeliveryService {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final Long WAITING_TIME = 500L;

    private static final Map<UUID,MockDeliveryResponse> CASH = new ConcurrentHashMap<>();

    private final MockDeliveryResponseGenerator generator;

    @Override
    public CompletableFuture<DeliveryResponse> createDelivery(DeliveryRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(WAITING_TIME);
                MockDeliveryResponse response = new MockDeliveryResponse(
                        generator.generateUUID(),
                        generator.generateDeliveryStatus(),
                        generator.generatePhone(),
                        generator.generateName()
                );
                CASH.putIfAbsent(response.getDeliveryId(),response);
                return response;
            } catch (InterruptedException e) {
                return MockDeliveryResponse.error();
            }
        });
    }

    @Override
    public CompletableFuture<DeliveryResponse> getCurrentStatus(UUID deliveryId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(WAITING_TIME);
                if(CASH.containsKey(deliveryId)){
                    return CASH.get(deliveryId);
                }
                throw new NotFoundDataException("Delivery not found");
            } catch (InterruptedException e) {
                return MockDeliveryResponse.error();
            }
        });
    }

    @Override
    public CompletableFuture<DeliveryResponse> approveDelivering(UUID deliveryId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (CASH.containsKey(deliveryId)) {
                    TimeUnit.MILLISECONDS.sleep(WAITING_TIME);
                    MockDeliveryResponse response = CASH.get(deliveryId);
                    if(response.getStatus() == DeliveryStatus.ON_THE_WAY) {
                        TimeUnit.MILLISECONDS.sleep(WAITING_TIME);
                        response.setStatus(DeliveryStatus.DELIVERED);
                    }
                    return response;
                }
                throw new NotFoundDataException("Delivery not found");
            } catch (InterruptedException e) {
                return MockDeliveryResponse.error();
            }
        });
    }

    @Override
    public CompletableFuture<DeliveryResponse> declineDelivery(UUID deliveryId) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                if (CASH.containsKey(deliveryId)) {
                    TimeUnit.MILLISECONDS.sleep(WAITING_TIME);
                    MockDeliveryResponse response = CASH.get(deliveryId);
                    if(response.getStatus() == DeliveryStatus.ON_THE_WAY) {
                        TimeUnit.MILLISECONDS.sleep(WAITING_TIME);
                        response.setStatus(DeliveryStatus.DECLINED);
                    }
                    return response;
                }
                throw new NotFoundDataException("Delivery not found");
            } catch (InterruptedException e) {
                return MockDeliveryResponse.error();
            }
        });
    }
}

