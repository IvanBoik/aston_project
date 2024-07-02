package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductListDTO;
import com.aston.aston_project.service.ProductListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product_lists")
@RequiredArgsConstructor
public class ProductListController {

    private final ProductListServiceImpl productListService;

    @PostMapping()
    public void createPL(@RequestBody ProductListDTO dto) {
        productListService.addProductList(dto);
    }

    @GetMapping()
    public List<ProductListDTO> allPLs() {
        return productListService.getAllProductLists();
    }

    @GetMapping(value = "/{id}")
    public ProductListDTO getPLById(@PathVariable Long id) {
       return productListService.getProductListByID(id);
    }
}
