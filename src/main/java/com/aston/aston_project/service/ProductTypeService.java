package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductTypeDTO;

import java.util.List;

public interface ProductTypeService {
    ProductTypeDTO getById(Long id);

    List<ProductTypeDTO> findByName(String name);

    List<ProductTypeDTO> getAll();

    void update(Long id, ProductTypeDTO dto);

    void create(ProductTypeDTO dto);

    void delete(Long id);
}