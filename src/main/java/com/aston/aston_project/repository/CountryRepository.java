package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountryIgnoreCaseContaining(String country);
}