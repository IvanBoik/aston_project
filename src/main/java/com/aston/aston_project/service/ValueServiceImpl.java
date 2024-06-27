package com.aston.aston_project.service;

import com.aston.aston_project.dto.ValueDTO;
import com.aston.aston_project.dto.util.ValueDTOMapping;
import com.aston.aston_project.entity.Value;
import com.aston.aston_project.repository.ValueRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValueServiceImpl implements ValueService {
    private final ValueRepository valueRepository;
    private final ValueDTOMapping valueDTOMapping;

    @Override
    public ValueDTO getById(Long id) {
        return valueDTOMapping.entityToDto(
                valueRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Vakue with id " + id + " not found")));
    }

    @Override
    public List<ValueDTO> findByName(String name) {
        return valueRepository.findByValueIgnoreCaseContaining(name).stream()
                .map(valueDTOMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ValueDTO> getAll() {
        return valueRepository.findAll().stream()
                .map(valueDTOMapping::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public void update(Long id, ValueDTO dto) {
        Optional<Value> optionalValue = valueRepository.findById(id);
        if (optionalValue.isPresent()) {
            Value value = optionalValue.get();
            value.setValue(dto.getName());
            valueRepository.save(value);
        } else {
            throw new NotFoundDataException("Value with id " + id + " not found");
        }
    }

    @Override
    public void create(ValueDTO dto) {
        valueRepository.save(valueDTOMapping.dtoToEntity(dto));
    }

    @Override
    public void delete(Long id) {
        valueRepository.deleteById(id);
    }
}