package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "recipes")
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private ProductList idProductList;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id")
    private Order idOrder;
}
