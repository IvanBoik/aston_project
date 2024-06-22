package com.aston.aston_project.repository;

import com.aston.aston_project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true, value = "select * from ROLE where name = 'ROLE_USER' limit 1")
    Role getRoleUser();
}
