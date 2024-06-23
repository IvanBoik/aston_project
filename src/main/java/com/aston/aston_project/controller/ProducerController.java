package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping("/{id}")
    public ProducerDtoResponse getById(@PathVariable Long id) {
        return producerService.getById(id);
    }

    @GetMapping
    public List<ProducerDtoResponse> getAll(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Long> country) {
        if (name.isPresent()) {
            return producerService.findByNameLike(name.get());
        }
        if (country.isPresent()) {
            return producerService.findByCountry(country.get());
        }
        return producerService.getAll();
    }

    @PostMapping
    public void add(@RequestBody ProducerDtoResponse dto) {
        producerService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProducerDtoResponse dto) {
        producerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        producerService.delete(id);
    }
}