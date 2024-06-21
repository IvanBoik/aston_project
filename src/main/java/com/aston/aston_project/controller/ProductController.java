package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDtoFull;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDtoFull getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    //todo поиск с фильтрами по названию, производителю, цене, наличию необходимости рецепта
    @GetMapping("")
    public List<ProductDtoShort> getAll() {
        return productService.getAll();
    }

    @PostMapping("")
    public void add(@RequestBody ProductDtoFull dto) {
        productService.add(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProductDtoFull dto) {
        productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}