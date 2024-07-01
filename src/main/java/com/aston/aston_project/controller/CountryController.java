package com.aston.aston_project.controller;

import com.aston.aston_project.dto.CountryDTO;
import com.aston.aston_project.service.CountryService;
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
@RequestMapping("/api/countries")
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/{id}")
    public CountryDTO getById(@PathVariable Long id) {
        return countryService.getById(id);
    }

    @GetMapping
    public List<CountryDTO> getAll(@RequestParam(required = false) Optional<String> name) {
        if (name.isPresent()) {
            return countryService.findByCountryName(name.get());
        }
        return countryService.getAll();
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody CountryDTO dto) {
        countryService.update(id, dto);
    }

    @PostMapping
    public void add(@RequestBody CountryDTO dto) {
        countryService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        countryService.delete(id);
    }
}