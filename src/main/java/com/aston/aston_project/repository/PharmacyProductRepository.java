package com.aston.aston_project.repository;

import com.aston.aston_project.entity.PharmacyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyProductRepository extends JpaRepository<PharmacyProduct, Long> {
}
