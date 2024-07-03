package com.aston.aston_project.chain;

import com.aston.aston_project.api.delivery.util.DeliveryRequest;
import com.aston.aston_project.api.delivery.util.DeliveryService;
import com.aston.aston_project.api.delivery.util.OrderInfo;
import com.aston.aston_project.api.delivery.util.UserInfo;
import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.OrderStatus;
import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DeliveryOrderFilter implements OrderFilter {
    private DeliveryService deliveryService;
    private OrderStatusRepository orderStatusRepository;
    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        if(request.getType() == OrderTypeEnum.DELIVERY){
            deliveryService.createDelivery(getRequest(user,order));
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

    private static DeliveryRequest getRequest(User user,Order order) {
        return new DeliveryRequest() {
            @Override
            public UserInfo getUserInfo() {
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
                        return order.getAddress().getCity();
                    }

                    @Override
                    public String getStreet() {
                        return order.getAddress().getStreet();
                    }

                    @Override
                    public String getHouse() {
                        return order.getAddress().getHouse();
                    }

                    @Override
                    public Short getRoom() {
                        return order.getAddress().getRoom();
                    }

                    @Override
                    public String getDeliveryComment() {
                        return "Доставка на дом";
                    }
                };
            }

            @Override
            public OrderInfo getOrderInfo() {
                return new OrderInfo() {
                    @Override
                    public Map<String, Integer> getProducts() {
                        return order.getProductList().stream().collect(Collectors.toMap(pl->pl.getProduct().getName(),ProductList::getCount));
                    }

                    @Override
                    public String getPharmacyAddress() {
                        return "Санкт-Петербург";
                    }
                };
            }
        };
    }
}
