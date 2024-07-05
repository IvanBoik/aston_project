package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductTypeDTO;
import com.aston.aston_project.service.ProductTypeService;
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
@RequestMapping("/api/v1/products/types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping("/{id}")
    public ProductTypeDTO getById(@PathVariable Long id) {
        log.info("Get request /api/v1/products/types/{}", id);
        return productTypeService.getById(id);
    }

    @GetMapping
    public List<ProductTypeDTO> getAll(@RequestParam(required = false) Optional<String> name) {
        log.info("Get request /api/v1/products/types/{}", name);
        if (name.isPresent()) {
            return productTypeService.findByName(name.get());
        }
        return productTypeService.getAll();
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProductTypeDTO dto) {
        log.info("Put request /api/v1/products/types/{}, {}", id, dto.toString());
        productTypeService.update(id, dto);
    }

    @PostMapping
    public void add(@RequestBody ProductTypeDTO dto) {
        log.info("Post request /api/v1/products/types/{}", dto.toString());
        productTypeService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete request /api/v1/products/types/{}", id);
        productTypeService.delete(id);
    }
}