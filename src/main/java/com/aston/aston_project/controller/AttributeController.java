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

import static com.aston.aston_project.AstonProjectApplication.log;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/attributes")
public class AttributeController {
    private final AttributeService attributeService;

    @GetMapping("/{id}")
    public AttributeDTO getById(@PathVariable Long id) {
        log.info("GET request /api/v1/products/attributes/{}", id);
        return attributeService.getById(id);
    }

    @GetMapping
    public List<AttributeDTO> getAll(@RequestParam(required = false) Optional<String> name) {
        log.info("GET request /api/v1/products/attributes");
        if (name.isPresent()) {
            return attributeService.findByName(name.get());
        }
        return attributeService.getAll();
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody AttributeDTO dto) {
        log.info("Put request /api/v1/products/attributes/{}", id);
        attributeService.update(id, dto);
    }

    @PostMapping
    public void create(@RequestBody AttributeDTO dto) {
        log.info("Post request /api/v1/products/attributes/{}", dto.toString());
        attributeService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete request /api/v1/products/attributes/{}", id);
        attributeService.delete(id);
    }
}