package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameIgnoreCaseContaining(String namePart);

    List<Product> findByProducer(Producer producer);

    List<Product> findByNameIgnoreCaseContainingAndProducer(String name, Producer producer);

    List<Product> findByNameIgnoreCaseContainingAndIsPrescriptionRequired(String name, Boolean isPrescriptionRequired);

    List<Product> findByProducerAndIsPrescriptionRequired(Producer producer, Boolean isPrescriptionRequired);

    @Query(value = """
    select p from Product p
    where (:name is null or lower(p.name) like lower(:name))
    and (:producer is null or :producer = p.producer.id)
    and (:recipe is null or :recipe = p.isPrescriptionRequired)
    """)
    List<Product> findAllWithFilters(
            @Param("name") String name,
            @Param("producer") Long producer,
            @Param("recipe") Boolean isPrescriptionRequired
    );
}