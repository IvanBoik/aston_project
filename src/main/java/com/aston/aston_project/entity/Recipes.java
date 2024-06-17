package com.aston.aston_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "recipes")
public class Recipes {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_recipe")
    @NotBlank(message = "link of recipe mustn't be the empty string")
    private String link;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private ProductList idProductList;

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id")
    private Order idOrder;
}
