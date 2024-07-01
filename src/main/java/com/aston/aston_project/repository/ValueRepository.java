package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {
    List<Value> findByValueIgnoreCaseContaining(String name);
}