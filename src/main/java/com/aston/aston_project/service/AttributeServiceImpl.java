package com.aston.aston_project.service;

import com.aston.aston_project.dto.AttributeDTO;
import com.aston.aston_project.dto.util.AttributeDTOMapping;
import com.aston.aston_project.entity.Attribute;
import com.aston.aston_project.repository.AttributeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeDTOMapping attributeDTOMapping;

    @Override
    public AttributeDTO getById(Long id) {
        return attributeDTOMapping.entityToDto(
                attributeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Product attribute with id " + id + " not found")));
    }

    @Override
    public List<AttributeDTO> findByName(String name) {
        return attributeRepository.findByAttributeIgnoreCaseContaining(name).stream()
                .map(attributeDTOMapping::entityToDto)
                .toList();
    }

    @Override
    public List<AttributeDTO> getAll() {
        return attributeRepository.findAll().stream()
                .map(attributeDTOMapping::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public void update(Long id, AttributeDTO dto) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product attribute with id " + id + " not found"));
        attribute.setAttribute(dto.getName());
        attributeRepository.save(attribute);
    }

    @Override
    public void create(AttributeDTO dto) {
        attributeRepository.save(attributeDTOMapping.dtoToEntity(dto));
    }

    @Override
    public void delete(Long id) {
        attributeRepository.deleteById(id);
    }
}