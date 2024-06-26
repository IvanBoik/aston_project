package com.aston.aston_project.api.delivery.client;

import com.aston.aston_project.api.delivery.util.DeliveryResponse;
import com.aston.aston_project.api.delivery.util.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MockDeliveryResponse implements DeliveryResponse {
    private UUID deliveryId;

    private DeliveryStatus status;

    private String courierPhone;

    private String courierName;

    public static MockDeliveryResponse error() {
        return new MockDeliveryResponse(UUID.randomUUID(),DeliveryStatus.OTHER,"","");
    }
}
