package com.aston.aston_project.repository;

import com.aston.aston_project.dto.OrderWithProductsDTO;
import com.aston.aston_project.entity.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductListRepository extends JpaRepository<ProductList,Long> {

    @Query(
            value = """
            SELECT DISTINCT pl.order as order,pl.product as product,sum(pl.count) over (partition by pl.product) as count FROM ProductList pl
            WHERE pl.product.isPrescriptionRequired IS TRUE
            """
    )
    List<OrderWithProductsDTO> getAllRecipeOrders();

    @Query("SELECT SUM(pl.count) FROM ProductList pl JOIN pl.order o JOIN pl.product p JOIN o.user u WHERE u.id = :userId AND p.product = :productName")
    Integer findTotalProductCountByUserAndProduct(@Param("userId") Long userId, @Param("productName") String productName);
}
