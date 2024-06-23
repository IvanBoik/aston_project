package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductDtoMapping {
    private final ProducerDtoMapping producerDtoMapping;

    public ProductDtoFullResponse entityToDtoFull(Product product) {
        ProductDtoFullResponse productDtoFull = new ProductDtoFullResponse();
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

    public Product dtoToEntity(ProductRequest dto) {
        Map<Attribute, Value> productEntityAttributes = new HashMap<>();
        for (Long att : dto.getAttributesValues().keySet()) {
            productEntityAttributes.put(new Attribute(att), new Value(dto.getAttributesValues().get(att)));
        }
        return Product.builder()
                .name(dto.getName())
                .type(new ProductType(dto.getType()))
                .isPrescriptionRequired(dto.getIsPrescriptionRequired())
                .price(dto.getPrice())
                .producer(new Producer(dto.getProducer()))
                .attributesValues(productEntityAttributes)
                .build();

    }
}