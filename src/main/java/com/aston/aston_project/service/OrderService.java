package com.aston.aston_project.service;

import com.aston.aston_project.dto.order.*;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.mapper.OrderMapper;
import com.aston.aston_project.repository.OrderRepository;
import com.aston.aston_project.repository.PaymentRepository;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.chain.OrderChain;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private UserService userService;
    private ProductRepository productRepository;
    private PharmacyRepository pharmacyRepository;
    private PaymentRepository paymentRepository;
    private OrderChain orderChain;

    public List<OrderWithAddressAndProductsDTO> getAllUserOrders(String user){
        return orderRepository.findByUserEmail(user).stream().map(orderMapper::toOrderWithAddressAndProductsDto).toList();
    }

    @Transactional
    public OrderWithAddressProductsAndRecipesDTO createOrder(String email, OrderCreateRequestDto order) {
        User user = userService.getUserByEmail(email);
        Order chainedOrder = orderChain.doFilter(user, order);
        System.out.println(chainedOrder);
        Order save = orderRepository.save(chainedOrder);
        return orderMapper.toOrderWithAddressProductsAndRecipesDTO(save);
    }
}
