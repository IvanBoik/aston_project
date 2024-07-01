package com.aston.aston_project.service;

import com.aston.aston_project.dto.CountryDTO;
import com.aston.aston_project.dto.util.CountryDTOMapping;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.repository.CountryRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final CountryDTOMapping countryDTOMapping;

    @Override
    public CountryDTO getById(Long id) {
        return countryDTOMapping.entityToDto(
                countryRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Country with id " + id + " not found")));
    }

    @Override
    public List<CountryDTO> findByCountryName(String country) {
        return countryRepository.findByCountryIgnoreCaseContaining(country).stream()
                .map(countryDTOMapping::entityToDto)
                .toList();
    }

    @Override
    public List<CountryDTO> getAll() {
        return countryRepository.findAll().stream()
                .map(countryDTOMapping::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public void update(Long id, CountryDTO dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Country with id " + id + " not found"));
        country.setCountry(dto.getName());
        countryRepository.save(country);
    }

    @Override
    public void create(CountryDTO countryDTO) {
        countryRepository.save(countryDTOMapping.dtoToEntity(countryDTO));
    }

    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}