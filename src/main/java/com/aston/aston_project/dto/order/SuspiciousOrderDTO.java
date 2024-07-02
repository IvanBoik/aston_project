package com.aston.aston_project.dto.order;

import com.aston.aston_project.entity.Product;

public interface SuspiciousOrderDTO {
    Long getId();
    Product getProduct();
    Long getCount();
}
