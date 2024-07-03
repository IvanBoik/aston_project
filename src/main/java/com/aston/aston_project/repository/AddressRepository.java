package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsById(Long id);
}
