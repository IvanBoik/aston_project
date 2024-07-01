package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.ValueDTO;
import com.aston.aston_project.entity.Value;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValueDTOMapping {
    public Value dtoToEntity(ValueDTO dto) {
        Value value = new Value();
        value.setValue(dto.getName());
        return value;
    }

    public ValueDTO entityToDto(Value value) {
        ValueDTO dto = new ValueDTO();
        dto.setName(value.getValue());
        return dto;
    }
}