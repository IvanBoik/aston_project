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

import static com.aston.aston_project.AstonProjectApplication.log;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {
    private OrderService orderService;

    @GetMapping
    public List<OrderWithAddressProductsAndRecipesDTO> getAllUserOrders(Principal principal) {
        log.info("Get request /api/v1/orders, {}", principal.getName());
        return orderService.getAllUserOrders(String.valueOf(principal));
    }

    @PostMapping
    public OrderWithAddressProductsAndRecipesDTO createOrder(Principal principal, @RequestBody @Valid OrderCreateRequestDto order) {
        log.info("Post request /api/v1/orders, {}, {}", principal.getName(), order.toString());
        return orderService.createOrder(String.valueOf(principal), order);
    }
}