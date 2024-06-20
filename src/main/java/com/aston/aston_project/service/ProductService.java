package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {


    ProductDto getById(Long id);

    List<Product> getAll();

    void add(Product product);

    void update(Long id, Product product);

    void delete(Long id);
}