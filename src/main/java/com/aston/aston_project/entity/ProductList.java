package com.aston.aston_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
@Table(name = "product_lists")
public class ProductList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private Product idProduct;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order idOrder;

    @Column(name = "count")
    @PositiveOrZero
    private Integer count;
}
