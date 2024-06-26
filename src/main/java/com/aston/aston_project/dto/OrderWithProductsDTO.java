package com.aston.aston_project.dto;

import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.Product;

public interface OrderWithProductsDTO {

    Order getOrder();
    Product getProduct();
    Integer getCount();
}
