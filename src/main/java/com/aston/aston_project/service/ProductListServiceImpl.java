package com.aston.aston_project.service;

import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.repository.ProductListRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductListServiceImpl {

    private final ProductListRepository productListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ProductList addProductList(ProductList productList) {
        return productListRepository.save(productList);
    }

    public List<ProductList> getAllProductLists() {
        List <ProductList> productLists = productListRepository.findAll();
        return productLists;
    }

    public ProductList getProductListByID(long id) {
        return productListRepository.findById(id)
                .orElseThrow(() -> {
                    new NotFoundDataException("No one product list found");
                    return null;
                });
    }

    //    Считает количество всех лекарств, заказанных пользователем
    public Integer findTotalProductCountByIdUser(Long id, String name) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) throw new NotFoundDataException("User not found with id " + id);

        return productListRepository.findTotalProductCountByUserAndProduct(id, name);
    }
}
