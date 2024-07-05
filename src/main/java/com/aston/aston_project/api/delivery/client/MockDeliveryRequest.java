package com.aston.aston_project.api.delivery.client;

import com.aston.aston_project.api.delivery.util.DeliveryRequest;
import com.aston.aston_project.api.delivery.util.OrderInfo;
import com.aston.aston_project.api.delivery.util.UserInfo;
import com.aston.aston_project.entity.*;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MockDeliveryRequest implements DeliveryRequest {

    private UserInfo userInfo;
    private OrderInfo orderInfo;

    public MockDeliveryRequest(User user,Address address, Order order) {
        this.userInfo = getUserInfoFromUser(user,address);
        this.orderInfo = getOrderInfoFromOrder(order);
    }

    private OrderInfo getOrderInfoFromOrder(Order order) {
        return new OrderInfo() {
            @Override
            public Map<String, Integer> getProducts() {
                return order.getProductList().stream().collect(Collectors.toMap(pl->pl.getProduct().getName(),ProductList::getCount));
            }

            @Override
            public String getPharmacyAddress() {
                return order.getAddress().getFullAddress();
            }
        };
    }

    private UserInfo getUserInfoFromUser(User user, Address address) {
        return new UserInfo() {
            @Override
            public String getName() {
                return user.getName();
            }

            @Override
            public String getPhone() {
                return user.getPhone();
            }

            @Override
            public String getCity() {
                return address.getCity();
            }

            @Override
            public String getStreet() {
                return address.getStreet();
            }

            @Override
            public String getHouse() {
                return address.getHouse();
            }

            @Override
            public Short getRoom() {
                return address.getRoom();
            }

            @Override
            public String getDeliveryComment() {
                return "mock delivery comment";
            }
        };
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
