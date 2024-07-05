package com.aston.aston_project.controller;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.order.OrderWithAddressProductsAndRecipesDTO;
import com.aston.aston_project.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public List<OrderWithAddressProductsAndRecipesDTO> getAllUserOrders(Principal principal){
       return orderService.getAllUserOrders(String.valueOf(principal));
    }

    @PostMapping
    public OrderWithAddressProductsAndRecipesDTO createOrder(Principal principal, @RequestBody @Valid OrderCreateRequestDto order){
        return orderService.createOrder(String.valueOf(principal),order);
    }

}
