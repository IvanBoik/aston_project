package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDto;
import com.aston.aston_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }//todo

//    @GetMapping("/{id}") //todo
//
//    @GetMapping("") //todo
//
//    @PutMapping("/{id}") //todo
//
//    @DeleteMapping("/{id}") //todo

}