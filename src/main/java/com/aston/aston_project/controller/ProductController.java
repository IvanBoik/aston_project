package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDto;
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
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    //todo поиск с фильтрами по названию, производителю, цене, наличию необходимости рецепта
    @GetMapping("")
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @PostMapping("")
    public void add(@RequestBody ProductDto dto) {
        productService.add(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProductDto dto) {
        productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}