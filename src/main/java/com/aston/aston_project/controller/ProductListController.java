package com.aston.aston_project.controller;

import com.aston.aston_project.service.ProductListServiceImpl;
import com.aston.aston_project.entity.ProductList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product_lists")
@RequiredArgsConstructor
public class ProductListController {

    private final ProductListServiceImpl productListCRUDService;
    private final ProductListServiceImpl productListService;

    @PostMapping()
    public ProductList createPL(@RequestBody ProductList productList) {
        productListCRUDService.addProductList(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body(productList).getBody();
    }

    @GetMapping()
    public List<ProductList> allPLs() {
        List<ProductList> productLists = productListCRUDService.getAllProductLists();

        if (productLists != null && !productLists.isEmpty()) {
            return ResponseEntity.ok(productLists).getBody();
        } else return (List<ProductList>) ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductList> getPLById(@PathVariable Long id) {
        ProductList productList = productListCRUDService.getProductListByID(id);

        return productList != null ? ResponseEntity.ok(productList) : ResponseEntity.noContent().build();
    }

    @GetMapping(value = "count_by_user/{id}")
    public Integer getTotalCount(@PathVariable Long id, @RequestParam String productName) {
        productListService.findTotalProductCountByIdUser(id, productName);

        return ResponseEntity.ok().build().getStatusCodeValue();
    }
}
