package com.aston.aston_project.service;

import com.aston.aston_project.dto.order.*;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.mapper.OrderMapper;
import com.aston.aston_project.repository.*;
import com.aston.aston_project.chain.OrderChain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private UserService userService;
    private OrderChain orderChain;

    public List<OrderWithAddressProductsAndRecipesDTO> getAllUserOrders(String user){
        return orderRepository.findByUserEmail(user).stream().map(orderMapper::toOrderWithAddressProductsAndRecipesDTO).toList();
    }

    @Transactional
    public OrderWithAddressProductsAndRecipesDTO createOrder(String email, OrderCreateRequestDto order) {
        User user = userService.getUserByEmail(email);
        Order chainedOrder = orderChain.doFilter(user, order);
        Order savedOrder = orderRepository.save(chainedOrder);
        return orderMapper.toOrderWithAddressProductsAndRecipesDTO(savedOrder);
    }
}
