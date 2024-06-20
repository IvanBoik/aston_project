package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.dto.util.ProductDtoMapping;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapping productDtoMapping;

    @Override
    public ProductDto getById(Long id) {
        return productDtoMapping.entityToDto(productRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(productDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public void add(ProductDto dto) {
        productRepository.save(productDtoMapping.dtoToEntity(dto));
    }

    @Override
    @Transactional
    public void update(Long id, ProductDto dto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product p = optionalProduct.get();
            p.setName(dto.getName());
            p.setPrice(dto.getPrice());
            p.setType(productDtoMapping.dtoToEntity(dto).getType());
            p.setIsPrescriptionRequired(dto.getIsPrescriptionRequired());
            p.setProducer(productDtoMapping.dtoToEntity(dto).getProducer());
            p.setAttributesValues(productDtoMapping.dtoToEntity(dto).getAttributesValues());
            productRepository.save(p);
        } else {
            throw new NotFoundDataException("Product not found");
        }
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}