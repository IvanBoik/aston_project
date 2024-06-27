package com.aston.aston_project.repository;

import com.aston.aston_project.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    List<ProductType> findByNameIgnoreCaseContaining(String name);
}