package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.entity.Country;
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
    public ProducerDto getById(@PathVariable Long id) {
        return producerService.getById(id);
    }

    // здесь как раз попыталась объединить через необязательные параметры запроса выдачу нефильтрованного списка всех производителей
    // и с фильтрами по названию или стране, не уверена, что идея/реализация вообще рабочие
    @GetMapping("")
    public List<ProducerDto> getAll(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Country> country) {
        if (name.isPresent()) {
            return producerService.findByNameLike(name.get());
        }
        if (country.isPresent()) {
            return producerService.findByCountry(country.get());
        }
        return producerService.getAll();
    }

    @PostMapping("")
    public void add(@RequestBody ProducerDto dto) {
        producerService.add(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody ProducerDto dto) {
        producerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        producerService.delete(id);
    }
}