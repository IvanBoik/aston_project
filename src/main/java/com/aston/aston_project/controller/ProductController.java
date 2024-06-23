package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDtoFullResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductDtoShort> getAll(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Long> producer,
            @RequestParam(required = false) Optional<Boolean> recipe
    ) {
        if (name.isPresent()) {
            return productService.findByNameIgnoreCaseContaining(name.get());
        }
        if (producer.isPresent()) {
            return productService.findByProducer(producer.get());
        }
        if (recipe.isPresent()) {
            return productService.findByRecipe(recipe.get());
        }
        return productService.getAll();
    }

    @PostMapping
    public void add(@RequestBody ProductRequest dto) {
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