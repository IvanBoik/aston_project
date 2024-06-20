package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.entity.Producer;

import java.util.List;

public interface ProducerService {

    ProducerDto getById(Long id);

    List<Producer> getAll();

    void add(Producer producer);

    void update(Long id, Producer producer);

    void delete(Long id);
}