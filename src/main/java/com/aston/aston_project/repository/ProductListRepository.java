package com.aston.aston_project.repository;

import com.aston.aston_project.entity.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductListRepository extends JpaRepository<ProductList, Long> {

    @Query("SELECT SUM(pl.count) FROM ProductList pl JOIN pl.order o JOIN pl.product p JOIN o.user u WHERE u.id = :userId AND p.product = :productName")
    Integer findTotalProductCountByUserAndProduct(@Param("userId") Long userId, @Param("productName") String productName);
}
