package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Long> producer,
            @RequestParam(required = false) Optional<Integer> recipe) {

        log.info("Get request /api/v1/products/{}, {}, {}", name, producer, recipe);

        if (name.isPresent()) {
            if (producer.isPresent()) {
                return productService.findByNameIgnoreCaseContainingAndProducer(name.get(), producer.get());
            }
            if (recipe.isPresent()) {
                return productService.findByNameIgnoreCaseContainingAndIsPrescriptionRequired(name.get(), recipe.get());
            }
            return productService.findByNameIgnoreCaseContaining(name.get());
        }
        if (producer.isPresent()) {
            if (recipe.isPresent()) {
                return productService.findByProducerAndIsPrescriptionRequired(producer.get(), recipe.get());
            }
            return productService.findByProducer(producer.get());
        }
        if (recipe.isPresent()) {
            return productService.findByRecipe(recipe.get());
        }
        return productService.getAll();
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete request /api/v1/products/{}", id);
        productService.delete(id);
    }
}