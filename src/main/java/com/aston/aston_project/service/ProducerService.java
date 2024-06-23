package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProducerService {

    ProducerDtoResponse getById(Long id);

    List<ProducerDtoResponse> getAll();

    void create(ProducerDtoResponse producerDto);

    void update(Long id, ProducerDtoResponse producerDto);

    void delete(Long id);

    List<ProducerDtoResponse> findByNameLike(String namePart);

    List<ProducerDtoResponse> findByCountry(Long countryId);
}