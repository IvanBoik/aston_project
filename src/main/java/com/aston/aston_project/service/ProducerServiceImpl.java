package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.dto.util.ProducerDtoMapping;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.repository.CountryRepository;
import com.aston.aston_project.repository.ProducerRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;
    private final ProducerDtoMapping producerDtoMapping;
    private final CountryRepository countryRepository;

    @Override
    public ProducerDtoResponse getById(Long id) {
        return producerDtoMapping.entityToDto(
                producerRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Producer with id " + id + " not found"))
        );
    }

    @Override
    public List<ProducerDtoResponse> getAll() {
        return producerRepository.findAll().stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDtoResponse> findByNameIgnoreCaseContaining(String namePart) {
        return producerRepository.findByNameIgnoreCaseContaining(namePart).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDtoResponse> findByCountry(Long countryId) {
        Country c = countryRepository.findById(countryId)
                .orElseThrow(() -> new NotFoundDataException("Producers from this country not found"));
        return producerRepository.findByCountry(c).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDtoResponse> findByNameIgnoreCaseContainingAndCountry(String name, Long countryId) {
        Country c = countryRepository.findById(countryId)
                .orElseThrow(() -> new NotFoundDataException("Producers from this country not found"));
        return producerRepository.findByNameIgnoreCaseContainingAndCountry(name, c).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public void create(ProducerDtoResponse dto) {
        Country c = getCountryIfExistsOrCreateNew(dto);
        producerRepository.save(producerDtoMapping.dtoToEntity(dto, c));
    }

    @Override
    @Transactional
    public void update(Long id, ProducerDtoResponse dto) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Producer with id " + id + " not found"));
        producer.setName(dto.getName());
        Country country = this.getCountryIfExistsOrCreateNew(dto);
        producer.setCountry(country);
        producerRepository.save(producer);
    }

    private Country getCountryIfExistsOrCreateNew(ProducerDtoResponse dto) {
        List<Country> list = countryRepository.findByCountryIgnoreCaseContaining(dto.getCountryName());
        Country c;
        if (list.stream().findAny().isEmpty()) {
            c = new Country();
            c.setCountry(dto.getCountryName());
            countryRepository.save(c);
        } else {
            c = list.stream().findAny().get();
        }
        return c;
    }

    @Override
    public void delete(Long id) {
        producerRepository.deleteById(id);
    }
}