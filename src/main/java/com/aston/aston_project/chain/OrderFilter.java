package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.User;

public interface OrderFilter {

    void process(User user, Order order, OrderCreateRequestDto request);
}
