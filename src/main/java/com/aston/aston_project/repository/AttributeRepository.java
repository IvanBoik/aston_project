package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findByAttributeIgnoreCaseContaining(String name);
}