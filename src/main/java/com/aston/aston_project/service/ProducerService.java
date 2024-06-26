package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDtoResponse;

import java.util.List;


public interface ProducerService {

    ProducerDtoResponse getById(Long id);

    List<ProducerDtoResponse> getAll();

    void create(ProducerDtoResponse producerDto);

    void update(Long id, ProducerDtoResponse producerDto);

    void delete(Long id);

    List<ProducerDtoResponse> findByNameIgnoreCaseContaining(String namePart);

    List<ProducerDtoResponse> findByCountry(Long countryId);

    List<ProducerDtoResponse> findByNameIgnoreCaseContainingAndCountry(String name, Long countryId);
}