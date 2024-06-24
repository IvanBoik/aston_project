package com.aston.aston_project.api.delivery.util;

import java.util.UUID;

public interface DeliveryResponse {

    UUID getDeliveryId();

    DeliveryStatus getStatus();

    String getCourierName();

    String getCourierPhone();
}
