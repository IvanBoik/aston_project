package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.entity.Attribute;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.Type;
import com.aston.aston_project.entity.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    public Product dtoToEntity(ProductDto dto) {
        Type t = new Type();
        t.setName(dto.getType());
        Map<Attribute, Value> productEntityAttributes = new HashMap<>();
        for(String attribute : dto.getAttributesValues().keySet()) {
            Attribute a = new Attribute();
            a.setAttribute(attribute);
            for(String value : dto.getAttributesValues().values()){
                Value v = new Value();
                v.setValue(value);
                productEntityAttributes.put(a,v);
            }
        }

        return Product.builder()
                .name(dto.getName())
                .type(t)
                .isPrescriptionRequired(dto.getIsPrescriptionRequired())
                .price(dto.getPrice())
                .producer(producerDtoMapping.dtoToEntity(dto.getProducer()))
                .attributesValues(productEntityAttributes)
                .build();
    }
}