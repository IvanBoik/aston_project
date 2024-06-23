package com.aston.aston_project.controller;

import com.aston.aston_project.crud.impl.ProductListCRUDServiceImpl;
import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.service.impl.productList.ProductListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product_list")
@RequiredArgsConstructor
public class ProductListController {

    private final ProductListCRUDServiceImpl productListCRUDService;
    private final ProductListServiceImpl productListService;

    @PostMapping(value = "/posts/new")
    public ProductList createPL(@RequestBody ProductList productList) {
        productListCRUDService.addProductList(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body(productList).getBody();
    }

    @GetMapping(value = "/posts")
    public List<ProductList> allPLs() {
        List<ProductList> productLists = productListCRUDService.getAllProductLists();

        if (productLists != null && !productLists.isEmpty()) {
            return ResponseEntity.ok(productLists).getBody();
        } else return (List<ProductList>) ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<ProductList> getPLById(@PathVariable Long id) {
        ProductList productList = productListCRUDService.getProductListByID(id);

        return productList != null ? ResponseEntity.ok(productList) : ResponseEntity.noContent().build();
    }

    @GetMapping(value = "count-by-user/{id}")
    public Integer getTotalCount(@PathVariable Long id, @RequestParam String productName) {
        productListService.findTotalProductCountByIdUser(id, productName);

        return ResponseEntity.ok().build().getStatusCodeValue();
    }
}
