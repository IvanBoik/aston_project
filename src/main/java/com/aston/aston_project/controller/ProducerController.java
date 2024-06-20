package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping("/{id}")
    public ResponseEntity<ProducerDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(producerService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // здесь как раз попыталась объединить через необязательные параметры запроса выдачу нефильтрованного списка всех производителей
    // и с фильтрами по названию или стране, не уверена, что идея/реализация вообще рабочие
    @GetMapping
    public List<Producer> getListOfProducers(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Country> country) {
        if (name.isPresent()) {
//            return producerService.findByNameLike(name.get());
        }
        if (country.isPresent()) {
//            return producerService.findByCountry(country.get());
        }
        return producerService.getAll();
    }

    @PostMapping
    public void addProducer(@RequestBody ProducerDto dto) {
//        producerService.add(dtoMapping.dtoToEntity(dto));//не должно быть лишней логики в контроллере
    }

    @PutMapping("/{id}")
    public void updateProducer(
            @PathVariable Long id,
            @RequestBody Producer producer) {
        producerService.update(id, producer);
    }

    @DeleteMapping("/{id}")
    public void deleteProducer(@PathVariable Long id) {
        producerService.delete(id);
    }
}