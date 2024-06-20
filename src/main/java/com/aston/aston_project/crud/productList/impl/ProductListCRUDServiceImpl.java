package com.aston.aston_project.crud.productList.impl;

import com.aston.aston_project.crud.productList.ProductListCRUDService;
import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.repository.ProductListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductListCRUDServiceImpl implements ProductListCRUDService {

    private final ProductListRepository productListRepository;

    @Override
    public ProductList addProductList(ProductList productList) {
        return productListRepository.save(productList);
    }

    @Override
    public List<ProductList> getAllProductLists() {
        Optional<List <ProductList>> productLists = Optional.of(productListRepository.findAll());
        return productLists
                .orElseThrow(() -> {
                    new NullPointerException("No one product list found");
                    return null;
                });
    }

    @Override
    public ProductList getProductListByID(long id) {
        return productListRepository.findById(id)
                .orElseThrow(() -> {
                    new NullPointerException("No one product list found");
                    return null;
                });
    }
}
