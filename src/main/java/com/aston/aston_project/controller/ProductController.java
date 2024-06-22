package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
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
    public ProductDtoFullResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    //todo поиск с фильтрами по названию, производителю, цене, наличию необходимости рецепта
    @GetMapping
    public List<ProductDtoShort> getAll() {
        return productService.getAll();
    }

    @PostMapping
    public void add(@RequestBody ProductRequest dto) {
        //todo должнабыть проверка Header на role = manager
        productService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProductRequest dto) {
        productService.update(id, dto);
    }
    @PutMapping
    public void updateRecipe(
            @RequestParam Long id,
            @RequestParam Boolean isRecipe) {
        productService.updateRecipe(id, isRecipe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}