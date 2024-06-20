package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProducerService {

    ProducerDto getById(Long id);

    List<ProducerDto> getAll();

    void add(ProducerDto producerDto);

    void update(Long id, ProducerDto producerDto);

    void delete(Long id);

    List<ProducerDto> findByNameLike(String s);

    List<ProducerDto> findByCountry(Country country);
}