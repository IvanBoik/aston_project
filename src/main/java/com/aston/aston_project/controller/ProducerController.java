package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.dto.util.ProducerDtoMapping;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.service.ProducerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProducerController {

    private final ProducerServiceImpl producerService;

    private final ProducerDtoMapping dtoMapping;

    @PostMapping("/producer")
    public void addProducer(@RequestBody ProducerDto dto) {
        producerService.add(dtoMapping.dtoToEntity(dto));
    }

    @GetMapping("/producer/{id}")
    public Producer getById(@PathVariable("id") Long id) {
        return producerService.getById(id);
    }

    // здесь как раз попыталась объединить через необязательные параметры запроса выдачу нефильтрованного списка всех производителей
    // и с фильтрами по названию или стране, не уверена, что идея/реализация вообще рабочие
    @GetMapping("/producers")
    public List<Producer> getListOfProducers(
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

    @PutMapping("/producer/{id}")
    public void updateProducer(
            @PathVariable("id") Long id,
            @RequestBody Producer producer) {
        producerService.update(id, producer);
    }

    @DeleteMapping("/producer/{id}")
    public void deleteProducer(@PathVariable("id") Long id) {
        producerService.delete(id);
    }
}