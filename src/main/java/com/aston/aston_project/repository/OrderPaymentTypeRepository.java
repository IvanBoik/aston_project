package com.aston.aston_project.repository;

import com.aston.aston_project.entity.OrderPaymentType;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderPaymentTypeRepository extends JpaRepository<OrderPaymentType,Long> {

    Optional<OrderPaymentType> findByName(OrderPaymentEnum name);
}
