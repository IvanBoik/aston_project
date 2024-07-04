package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.CountryDTO;
import com.aston.aston_project.entity.Country;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CountryDTOMapping {

    public Country dtoToEntity(CountryDTO dto) {
        Country country = new Country();
        country.setCountry(dto.getName());
        return country;
    }

    public CountryDTO entityToDto(Country country) {
        CountryDTO dto = new CountryDTO();
        dto.setName(country.getCountry());
        return dto;
    }
}