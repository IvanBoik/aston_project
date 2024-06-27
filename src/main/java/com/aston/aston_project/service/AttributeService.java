package com.aston.aston_project.service;

import com.aston.aston_project.dto.AttributeDTO;

import java.util.List;

public interface AttributeService {
    AttributeDTO getById(Long id);

    List<AttributeDTO> findByName(String name);

    List<AttributeDTO> getAll();

    void update(Long id, AttributeDTO dto);

    void create(AttributeDTO dto);

    void delete(Long id);
}