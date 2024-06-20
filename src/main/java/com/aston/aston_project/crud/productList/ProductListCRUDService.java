package com.aston.aston_project.crud.productList;

import com.aston.aston_project.entity.ProductList;

import java.util.List;

public interface ProductListCRUDService {

    ProductList addProductList(ProductList productList);
    List<ProductList> getAllProductLists();
    ProductList getProductListByID(long id);
    void updateProductListByID(long id, ProductList productList);
}
