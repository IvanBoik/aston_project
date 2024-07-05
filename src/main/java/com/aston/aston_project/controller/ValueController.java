package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ValueDTO;
import com.aston.aston_project.service.ValueService;
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
@RequestMapping("/api/v1/products/values")
public class ValueController {
    private final ValueService valueService;

    @GetMapping("/{id}")
    public ValueDTO getById(@PathVariable Long id) {
        log.info("Get request /api/v1/products/values/{}", id);
        return valueService.getById(id);
    }

    @GetMapping
    public List<ValueDTO> getAll(@RequestParam(required = false) Optional<String> name) {
        log.info("Get request /api/v1/products/values/{}", name);
        if (name.isPresent()) {
            return valueService.findByName(name.get());
        }
        return valueService.getAll();
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ValueDTO dto) {
        log.info("Put request /api/v1/products/values/{}, {}", id, dto.toString());
        valueService.update(id, dto);
    }

    @PostMapping
    public void add(@RequestBody ValueDTO dto) {
        log.info("Post request /api/v1/products/values/{}", dto.toString());
        valueService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete request /api/v1/products/values/{}", id);
        valueService.delete(id);
    }
}