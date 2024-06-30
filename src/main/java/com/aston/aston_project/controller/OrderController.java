package com.aston.aston_project.controller;

import com.aston.aston_project.dto.OrderExtendedResponseDTO;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.UserResponseDTO;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/orders")
@AllArgsConstructor
public class OrderController {

    OrderService orderService;

    @PostMapping()
    public Order createOrder(@RequestBody Order order, @RequestBody UserResponseDTO user, @RequestBody ProductDtoShort product) {
        orderService.addOrder(order, user, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(order).getBody();
    }

    @GetMapping()
    public List<OrderExtendedResponseDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping("/address/{id}")
    public List<OrderExtendedResponseDTO> getOrderByAddressId(@PathVariable Long id) {
        return orderService.findByAddress(id);
    }

    @PutMapping("/{id}")
    public void updateOrder(
            @PathVariable Long id, @RequestBody  OrderExtendedResponseDTO orderDTO, @RequestBody ProductDtoShort product) {
        orderService.updateOrder(id, orderDTO, product);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
    }
}
