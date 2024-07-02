package com.aston.aston_project.service;

import com.aston.aston_project.dto.CountryDTO;


import java.util.List;

public interface CountryService {
    CountryDTO getById(Long id);

    List<CountryDTO> findByCountryName(String country);

    List<CountryDTO> getAll();

    void update(Long id, CountryDTO dto);

    void create(CountryDTO countryDTO);

    void delete(Long id);
}