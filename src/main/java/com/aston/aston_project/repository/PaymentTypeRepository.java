package com.aston.aston_project.repository;

import com.aston.aston_project.entity.PaymentType;
import com.aston.aston_project.entity.en.PaymentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {

    Optional<PaymentType> findByType(PaymentTypeEnum type);
}
