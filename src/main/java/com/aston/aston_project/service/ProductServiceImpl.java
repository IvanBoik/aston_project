package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.dto.util.ProductDtoMapping;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapping productDtoMapping;


    @Override
    public ProductDto getById(Long id) {
        return productDtoMapping.entityToDto(productRepository.findById(id).orElseThrow());
    }

    //todo
    @Override
    public List<Product> getAll() {
        return List.of();
    }

    //todo
    @Override
    public void add(Product product) {
    }
    //todo
    @Override
    @Transactional
    public void update(Long id, Product product) {

    }

    //todo
    @Override
    public void delete(Long id) {

    }
}