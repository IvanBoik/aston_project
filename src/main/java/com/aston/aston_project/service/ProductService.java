package com.aston.aston_project.service;

import com.aston.aston_project.entity.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    Product getById(Long id);

    List<Product> getAll();

    void update(Long id, Product product);

    void delete(Long id);
}