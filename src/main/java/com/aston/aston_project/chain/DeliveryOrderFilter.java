package com.aston.aston_project.chain;

import com.aston.aston_project.api.delivery.client.MockDeliveryRequest;
import com.aston.aston_project.api.delivery.util.DeliveryRequest;
import com.aston.aston_project.api.delivery.util.DeliveryService;
import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.Address;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.OrderStatus;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * filter which creates delivery and setting order status depends on order request order status
 * @author K. Zemlyakov
 */
@Component
@AllArgsConstructor
public class DeliveryOrderFilter implements OrderFilter {
    private DeliveryService deliveryService;
    private OrderStatusRepository orderStatusRepository;
    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        if(request.getType() == OrderTypeEnum.DELIVERY){
            deliveryService.createDelivery(getRequest(user,order.getAddress(),order));
            OrderStatus orderStatus = orderStatusRepository.findByStatus(OrderStatusEnum.IN_PROCESS)
                    .orElse(OrderStatus.builder().status(OrderStatusEnum.IN_PROCESS).build());
            order.setStatus(orderStatus);
        }else{
            OrderStatus orderStatus = orderStatusRepository.findByStatus(OrderStatusEnum.DRAFT)
                    .orElse(OrderStatus.builder().status(OrderStatusEnum.DRAFT).build());
            order.setStatus(orderStatus);
        }
        order.setUser(user);
    }

    private static DeliveryRequest getRequest(User user, Address address, Order order) {
        return new MockDeliveryRequest(user,address,order);
    }
}
