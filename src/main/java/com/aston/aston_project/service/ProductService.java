package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {


    ProductDtoFullResponse getById(Long id);

    List<ProductDtoShort> getAll();

    void create(ProductRequest productRequest);

    void update(Long id, ProductRequest productRequest);

    void updateRecipe(Long id, Boolean isRecipe);

    void delete(Long id);
}