package com.aston.aston_project.api.delivery.client;

import com.aston.aston_project.api.delivery.util.DeliveryRequest;
import com.aston.aston_project.api.delivery.util.OrderInfo;
import com.aston.aston_project.api.delivery.util.UserInfo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MockDeliveryRequest implements DeliveryRequest {

    private UserInfo userInfo;
    private OrderInfo orderInfo;
    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
