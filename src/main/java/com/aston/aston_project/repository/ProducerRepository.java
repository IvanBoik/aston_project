package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findByNameIgnoreCaseContaining(String namePart);

    List<Producer> findByCountry(Country country);

    List<Producer> findByNameIgnoreCaseContainingAndCountry(String name, Country country);
}