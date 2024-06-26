package com.aston.aston_project.repository;

import com.aston.aston_project.dto.SuspiciousOrderDTO;
import com.aston.aston_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
           SELECT o.id as id,p as product,sum(pl.count) as count FROM Order o
           JOIN ProductList pl on o.id = pl.order.id
           JOIN Product p on p.id = pl.product.id
           GROUP BY  o.id, p.id,p.name,p.price HAVING sum(pl.count) / p.price > 20 OR p.price / sum(pl.count)> 20
""")
    List<SuspiciousOrderDTO> getAllSuspiciousOrders();



}
