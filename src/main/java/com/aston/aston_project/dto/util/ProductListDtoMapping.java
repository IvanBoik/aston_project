package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProductListDTO;
import com.aston.aston_project.entity.ProductList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductListDtoMapping {
    public ProductList dtoToEntity (ProductListDTO dto) {
        return ProductList.builder()
                .product(dto.getProduct())
                .order(dto.getOrder())
                .count(dto.getCount())
                .build();
    }

    public ProductListDTO entityToDto (ProductList productList) {
        ProductListDTO productListDTO = new ProductListDTO();

        productListDTO.setProduct(productList.getProduct());
        productListDTO.setOrder(productList.getOrder());
        productListDTO.setCount(productList.getCount());
        return productListDTO;
    }
}
