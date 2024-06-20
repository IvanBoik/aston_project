package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // todo специфические методы для поиска с фильтрами
}