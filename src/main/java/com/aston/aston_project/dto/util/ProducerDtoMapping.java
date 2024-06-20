package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.entity.Producer;

public class ProducerDtoMapping {
    public Producer dtoToEntity(ProducerDto dto) {
        Producer producer = new Producer();
        producer.setName(dto.getName());
        producer.setCountry(dto.getCountry());
        return producer;
    }
}