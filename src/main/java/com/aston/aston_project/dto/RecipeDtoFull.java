package com.aston.aston_project.dto;

import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.ProductList;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecipeDtoFull {

    private String link;
    private ProductList productList;
    private Order order;
}
