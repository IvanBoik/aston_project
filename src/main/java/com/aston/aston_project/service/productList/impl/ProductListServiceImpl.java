package com.aston.aston_project.service.productList.impl;

import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.repository.ProductListRepository;
import com.aston.aston_project.service.productList.ProductListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductListServiceImpl implements ProductListService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductListRepository productListRepository;

    @Override
    public Integer findTotalProductCountByIdUser(Long id, String name) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Product> optionalProduct = productRepository.findByName(name);

        if (optionalUser.isEmpty()) throw new RuntimeException("User not found with id " + id);
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product not found with name " + name);

        return productListRepository.findTotalProductCountByUserAndProduct(id, name);
    }
}
