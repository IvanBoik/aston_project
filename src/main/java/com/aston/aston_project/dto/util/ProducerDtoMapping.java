package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProducerDtoMapping {
    private final CountryRepository countryRepository;

    public Producer dtoToEntity(ProducerDtoResponse dto) {
        Producer producer = new Producer();
        producer.setName(dto.getName());
        producer.setCountry(countryRepository.findByCountryIgnoreCaseContaining(dto.getCountryName()));
        return producer;
    }

    public ProducerDtoResponse entityToDto(Producer entity) {
        ProducerDtoResponse producerDto = new ProducerDtoResponse();
        producerDto.setName(entity.getName());
        producerDto.setCountryName(entity.getCountry().getCountry());
        return producerDto;
    }
}