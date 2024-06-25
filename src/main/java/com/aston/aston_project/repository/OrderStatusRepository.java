package com.aston.aston_project.repository;

import com.aston.aston_project.entity.OrderStatus;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {

    Optional<OrderStatus> findByStatus(OrderStatusEnum status);
}
