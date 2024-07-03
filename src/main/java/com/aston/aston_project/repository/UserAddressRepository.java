package com.aston.aston_project.repository;

import com.aston.aston_project.dto.UserAddressDTO;
import com.aston.aston_project.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query(value = "SELECT ua.id as id, u.name as name, u.surname as surname, a as address FROM UserAddress ua JOIN ua.user u WHERE u.id = :userId")
    List<UserAddressDTO> getAllUserAddressesByUserId(@Param("userId") Long userId);
}
