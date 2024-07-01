package com.aston.aston_project.api.delivery.util;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DeliveryService {


    CompletableFuture<DeliveryResponse> createDelivery(DeliveryRequest request);

    CompletableFuture<DeliveryResponse> getCurrentStatus(UUID deliveryId);

    CompletableFuture<DeliveryResponse> approveDelivery(UUID deliveryId);

    CompletableFuture<DeliveryResponse> declineDelivery(UUID deliveryId);
}
