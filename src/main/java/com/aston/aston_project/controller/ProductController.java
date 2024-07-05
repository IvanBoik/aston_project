package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.service.ProductService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aston.aston_project.AstonProjectApplication.log;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDtoFullResponse getById(@PathVariable Long id) {
        log.info("Get request /api/v1/products/{}", id);
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductDtoShort> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long producer,
            @RequestParam(required = false) Boolean recipe
    ) {
        return productService.findWithFilters(name, producer, recipe);
    }

    @PostMapping
    public void add(@RequestBody ProductRequest dto) {
        log.info("Post request /api/v1/products/{}", dto.toString());
        productService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProductRequest dto) {
        log.info("Put request /api/v1/products/{}, {}", id, dto.toString());
        productService.update(id, dto);
    }

    @PutMapping
    public void updateRecipe(
            @RequestParam Long id,
            @RequestParam Boolean isRecipe) {
        log.info("Put request /api/v1/products/{}, {}", id, isRecipe);
        productService.updateRecipe(id, isRecipe);
    }

    @PutMapping("/{productListId}/count/{count}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCount(
            @PathVariable @Positive Long productListId,
            @PathVariable @Positive Integer count
    ) {
        productService.updateCount(productListId, count);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete request /api/v1/products/{}", id);
        productService.delete(id);
    }
}