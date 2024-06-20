package com.aston.aston_project.controller;

import com.aston.aston_project.crud.productList.ProductListCRUDService;
import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.service.productList.ProductListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product_list")
@RequiredArgsConstructor
public class ProductListController {

    private final ProductListCRUDService productListCRUDService;
    private final ProductListService productListService;

    @PostMapping(value = "new_product_list")
    public ResponseEntity<ProductList> createPL(@RequestBody ProductList productList) {
        productListCRUDService.addProductList(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body(productList);
    }

    @GetMapping(value = "/product_list/all")
    public ResponseEntity<List<ProductList>> allPLs() {
        List<ProductList> productLists = productListCRUDService.getAllProductLists();

        if (productLists != null && !productLists.isEmpty()) {
            return ResponseEntity.ok(productLists);
        } else return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductList> getRecipeById(@PathVariable Long id) {
        ProductList productList = productListCRUDService.getProductListByID(id);

        return productList != null ? ResponseEntity.ok(productList) : ResponseEntity.noContent().build();
    }

    @GetMapping(value = "count-by-user/{id}")
    public ResponseEntity<Integer> getTotalCount(@PathVariable Long id, @RequestParam String productName) {
        productListService.findTotalProductCountByIdUser(id, productName);

        return ResponseEntity.ok().build();
    }
}
