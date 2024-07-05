package com.aston.aston_project.repository;

import com.aston.aston_project.entity.OrderType;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTypeRepository extends JpaRepository<OrderType,Long> {

    Optional<OrderType> findByName(OrderTypeEnum name);
}
