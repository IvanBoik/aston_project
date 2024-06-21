package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDtoFull;
import com.aston.aston_project.dto.ProductDtoShort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {


    ProductDtoFull getById(Long id);

    List<ProductDtoShort> getAll();

    void add(ProductDtoFull productDtoFull);

    void update(Long id, ProductDtoFull productDtoFull);

    void delete(Long id);
}