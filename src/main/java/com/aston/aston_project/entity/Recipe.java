package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductList productList;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;
}
