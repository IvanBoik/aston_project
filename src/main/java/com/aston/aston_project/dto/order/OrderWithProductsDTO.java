package com.aston.aston_project.dto.order;

import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.Product;

public interface OrderWithProductsDTO {

    Order getOrder();
    Product getProduct();
    Integer getCount();
}
