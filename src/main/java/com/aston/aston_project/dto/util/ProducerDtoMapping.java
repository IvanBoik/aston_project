package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerDtoMapping {
    public Producer dtoToEntity(ProducerDtoResponse dto) {
        Producer producer = new Producer();
        producer.setName(dto.getName());
        Country country = new Country();
        country.setCountry(dto.getCountryName());
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