package com.aston.aston_project.service;

import com.aston.aston_project.dto.OrderExtendedResponseDTO;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.UserResponseDTO;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.mapper.OrderMapper;
import com.aston.aston_project.repository.OrderRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import com.aston.aston_project.util.exception.PrescriptionRequiredException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public void addOrder(Order order, UserResponseDTO user, ProductDtoShort product) {


        if (user == null) {
            if (product.getIsPrescriptionRequired()) {
                throw new PrescriptionRequiredException("Unauthorized user cannot create order for prescription required product");
            }
        }

        orderMapper.toExtendedDTO(orderRepository.save(order));
    }

    public List<OrderExtendedResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toExtendedDTO).toList();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Order " + id + " not found")
                );
    }

    @Transactional
    public void updateOrder(Long id, OrderExtendedResponseDTO orderDto, ProductDtoShort product) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Order " + id + " not found"));
        if (orderDto.getPaymentType() != null) {
            order.setPaymentType(new OrderPaymentType());
        }
        if (orderDto.getType() != null) {
            order.setType(new OrderType());
        }

        addOrder(order, orderDto.getUser(), product);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderExtendedResponseDTO> findByAddress(Long address) {
        return orderRepository.findAll().stream()
                .filter(a -> a.getAddress().getId().equals(address))
                .map(orderMapper::toExtendedDTO)
                .toList();
    }
}
