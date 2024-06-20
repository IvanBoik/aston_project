package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.entity.Attribute;
import com.aston.aston_project.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDtoMapping {
    private final ProducerDtoMapping producerDtoMapping;

    public ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setType(product.getType().getName());
        productDto.setIsPrescriptionRequired(product.getIsPrescriptionRequired());
        productDto.setProducer(producerDtoMapping.entityToDto(product.getProducer()));
        for (Attribute key : product.getAttributesValues().keySet()) {
            productDto.setAttributesValues(key.getAttribute(), product.getAttributesValues().get(key).getValue());
        }
        return productDto;
    }
}
