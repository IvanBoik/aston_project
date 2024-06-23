package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.dto.util.ProductDtoMapping;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.repository.*;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapping productDtoMapping;
    private final ProducerRepository producerRepository;

    @Override
    public ProductDtoFullResponse getById(Long id) {
        return productDtoMapping.entityToDtoFull(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"))
        );
    }

    @Override
    public List<ProductDtoShort> getAll() {
        return productRepository.findAll().stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    @Override
    public List<ProductDtoShort> findByNameIgnoreCaseContaining(String namePart) {
        return productRepository.findByNameIgnoreCaseContaining(namePart).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    @Override
    public List<ProductDtoShort> findByProducer(Long producerId) {
        Producer p = producerRepository.findById(producerId)
                .orElseThrow(() -> new NotFoundDataException("No data for such a producer found"));
        return productRepository.findByProducer(p).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    @Override
    public List<ProductDtoShort> findByRecipe(Boolean isPrescriptionRequired) {
        return productRepository.findAll().stream()
                .filter(p -> p.getIsPrescriptionRequired() == isPrescriptionRequired)
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    @Override
    public void create(ProductRequest dto) {
        productRepository.save(productDtoMapping.dtoToEntity(dto));
    }

    @Override
    @Transactional
    public void update(Long id, ProductRequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));
        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getType() != null) {
            product.setType(new ProductType(dto.getType()));
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getProducer() != null) {
            product.setProducer(new Producer(dto.getProducer()));
        }
        if (!dto.getAttributesValues().isEmpty()) {
            for (Long att : dto.getAttributesValues().keySet()) {
                product.setAttributesValues(new Attribute(att), new Value(dto.getAttributesValues().get(att)));
            }
        }
        productRepository.save(product);
    }

    @Override
    public void updateRecipe(Long id, Boolean isRecipe) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));
        product.setIsPrescriptionRequired(isRecipe);
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}