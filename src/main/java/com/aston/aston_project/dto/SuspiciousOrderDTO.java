package com.aston.aston_project.dto;

import com.aston.aston_project.entity.Product;

public interface SuspiciousOrderDTO {
    Long getId();
    Product getProduct();
    Long getCount();
}
