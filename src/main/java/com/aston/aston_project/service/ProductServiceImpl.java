package com.aston.aston_project.service;

import com.aston.aston_project.entity.Product;
import com.aston.aston_project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    //todo
    @Override
    public void add(Product product) {
    }

    //todo
    @Override
    public Product getById(Long id) {
        return null;
    }

    //todo
    @Override
    public List<Product> getAll() {
        return List.of();
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