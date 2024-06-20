package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {


    ProductDto getById(Long id);

    List<ProductDto> getAll();

    void add(ProductDto productDto);

    void update(Long id, ProductDto productDto);

    void delete(Long id);
}