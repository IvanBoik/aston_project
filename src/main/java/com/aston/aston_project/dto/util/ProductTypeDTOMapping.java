package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProductTypeDTO;
import com.aston.aston_project.entity.ProductType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductTypeDTOMapping {
    public ProductType dtoToEntity(ProductTypeDTO dto) {
        ProductType type = new ProductType();
        type.setName(dto.getName());
        return type;
    }

    public ProductTypeDTO entityToDto(ProductType type) {
        ProductTypeDTO dto = new ProductTypeDTO();
        dto.setName(type.getName());
        return dto;
    }
}