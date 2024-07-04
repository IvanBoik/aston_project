package com.aston.aston_project.controller;

import com.aston.aston_project.dto.AttributeDTO;
import com.aston.aston_project.service.AttributeService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/attributes")
public class AttributeController {
    private final AttributeService attributeService;

    @GetMapping("/{id}")
    public AttributeDTO getById(@PathVariable Long id) {
        return attributeService.getById(id);
    }

    @GetMapping
    public List<AttributeDTO> getAll(@RequestParam(required = false) Optional<String> name) {
        if (name.isPresent()) {
            return attributeService.findByName(name.get());
        }
        return attributeService.getAll();
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody AttributeDTO dto) {
        attributeService.update(id, dto);
    }

    @PostMapping
    public void create(@RequestBody AttributeDTO dto) {
        attributeService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        attributeService.delete(id);
    }
}