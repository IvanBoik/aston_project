package com.aston.aston_project.service;

import com.aston.aston_project.dto.ValueDTO;

import java.util.List;

public interface ValueService {
    ValueDTO getById(Long id);

    List<ValueDTO> findByName(String name);

    List<ValueDTO> getAll();

    void update(Long id, ValueDTO dto);

    void create(ValueDTO dto);

    void delete(Long id);
}