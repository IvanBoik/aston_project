package com.aston.aston_project.repository;

import com.aston.aston_project.dto.OrderWithProductsDTO;
import com.aston.aston_project.entity.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductListRepository extends JpaRepository<ProductList,Long> {

    @Query(
            value = """
            SELECT DISTINCT pl.order as order,pl.product as product,sum(pl.count) over (partition by pl.product) as count FROM ProductList pl
            WHERE pl.product.isPrescriptionRequired IS TRUE
            """
    )
    List<OrderWithProductsDTO> getAllRecipeOrders();
}
