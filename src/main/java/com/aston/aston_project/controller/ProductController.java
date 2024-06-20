package com.aston.aston_project.controller;

import com.aston.aston_project.service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/product") //todo

    @GetMapping("/product/{id}") //todo

    @GetMapping("/products") //todo

    @PutMapping("/product/{id}") //todo

    @DeleteMapping("/product/{id}") //todo

}