package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.SimpleProductResponseDTO;
import com.aston.aston_project.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    SimpleProductResponseDTO toSimpleProductDTO(Product product);
}
