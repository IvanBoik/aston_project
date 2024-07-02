package com.aston.aston_project.dto;

import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductListDTO {

    private Product product;
    private Order order;
    private Integer count;
}
