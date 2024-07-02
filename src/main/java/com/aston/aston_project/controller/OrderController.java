package com.aston.aston_project.controller;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.order.OrderWithAddressProductsAndRecipesDTO;
import com.aston.aston_project.dto.order.OrderWithAddressAndProductsDTO;
import com.aston.aston_project.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public List<OrderWithAddressAndProductsDTO> getAllUserOrders(Authentication authentication){
       return orderService.getAllUserOrders((String) authentication.getPrincipal());
    }

    @PostMapping
    public OrderWithAddressProductsAndRecipesDTO createOrder(Authentication authentication, @RequestBody @Valid OrderCreateRequestDto order){
        return orderService.createOrder(((String) authentication.getPrincipal()),order);
    }

}
