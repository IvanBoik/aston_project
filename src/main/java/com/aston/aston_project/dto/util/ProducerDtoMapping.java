package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProducerDtoMapping {
    public Producer dtoToEntity(ProducerDtoResponse dto, Country country) {
        Producer producer = new Producer();
        producer.setName(dto.getName());
        producer.setCountry(country);
        return producer;
    }

    public ProducerDtoResponse entityToDto(Producer entity) {
        ProducerDtoResponse producerDto = new ProducerDtoResponse();
        producerDto.setName(entity.getName());
        producerDto.setCountryName(entity.getCountry().getCountry());
        return producerDto;
    }
}