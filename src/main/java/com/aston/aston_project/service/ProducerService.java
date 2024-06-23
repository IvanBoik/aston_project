package com.aston.aston_project.service;

import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.dto.*;

import java.util.List;

public interface ProducerService {
    void add(ProducerDto producerDto);

    Producer getById(Long id);

    List<Producer> getAll();

    void update(Long id, Producer producer);

    void delete(Long id);
}