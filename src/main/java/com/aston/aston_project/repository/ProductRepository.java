package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameIgnoreCaseContaining(String namePart);

    List<Product> findByProducer(Producer producer);

    List<Product> findByNameIgnoreCaseContainingAndProducer(String name, Producer producer);

    List<Product> findByNameIgnoreCaseContainingAndIsPrescriptionRequired(String name, Boolean isPrescriptionRequired);

    List<Product> findByProducerAndIsPrescriptionRequired(Producer producer, Boolean isPrescriptionRequired);
}