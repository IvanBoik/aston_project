package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;

import java.util.List;

public interface ProductService {

    ProductDtoFullResponse getById(Long id);

    List<ProductDtoShort> getAll();

    void create(ProductRequest productRequest);

    void update(Long id, ProductRequest productRequest);

    void updateRecipe(Long id, Boolean isRecipe);

    void delete(Long id);

    List<ProductDtoShort> findByNameIgnoreCaseContaining(String namePart);

    List<ProductDtoShort> findByProducer(Long producerId);

    List<ProductDtoShort> findByRecipe(Integer recipe);

    List<ProductDtoShort> findByNameIgnoreCaseContainingAndProducer(String name, Long producerId);

    List<ProductDtoShort> findByNameIgnoreCaseContainingAndIsPrescriptionRequired(String name, Integer integer);

    List<ProductDtoShort> findByProducerAndIsPrescriptionRequired(Long producerId, Integer recipe);
}