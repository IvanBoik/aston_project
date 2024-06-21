package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProductDtoFull;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.entity.Attribute;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.ProductType;
import com.aston.aston_project.entity.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductDtoMapping {
    private final ProducerDtoMapping producerDtoMapping;

    public ProductDtoFull entityToDtoFull(Product product) {
        ProductDtoFull productDtoFull = new ProductDtoFull();
        productDtoFull.setName(product.getName());
        productDtoFull.setPrice(product.getPrice());
        productDtoFull.setType(product.getType().getName());
        productDtoFull.setIsPrescriptionRequired(product.getIsPrescriptionRequired());
        productDtoFull.setProducer(producerDtoMapping.entityToDto(product.getProducer()));
        for (Attribute key : product.getAttributesValues().keySet()) {
            productDtoFull.setAttributesValues(key.getAttribute(), product.getAttributesValues().get(key).getValue());
        }
        return productDtoFull;
    }

    public ProductDtoShort entityToDtoShort(Product product) {
        ProductDtoShort productDtoShort = new ProductDtoShort();
        productDtoShort.setName(product.getName());
        productDtoShort.setPrice(product.getPrice());
        productDtoShort.setType(product.getType().getName());
        productDtoShort.setIsPrescriptionRequired(product.getIsPrescriptionRequired());
        productDtoShort.setProducer(producerDtoMapping.entityToDto(product.getProducer()));
        return productDtoShort;
    }

    public Product dtoToEntity(ProductDtoFull dto) {
        ProductType t = new ProductType();
        t.setName(dto.getType());
        Map<Attribute, Value> productEntityAttributes = new HashMap<>();
        for (String attribute : dto.getAttributesValues().keySet()) {
            Attribute a = new Attribute();
            a.setAttribute(attribute);
            for (String value : dto.getAttributesValues().values()) {
                Value v = new Value();
                v.setValue(value);
                productEntityAttributes.put(a, v);
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