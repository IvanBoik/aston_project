package com.aston.aston_project.repository;

import com.aston.aston_project.dto.UserEmailDto;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);

    @Query(value = """
    select u.email as email from User u
    where :product in elements(u.wishList)
    """)
    List<UserEmailDto> findAllByWishListContaining(@Param("product") Product product);
}
