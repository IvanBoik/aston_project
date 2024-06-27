package com.aston.aston_project.dto.util;

import com.aston.aston_project.dto.AttributeDTO;
import com.aston.aston_project.entity.Attribute;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttributeDTOMapping {
    public Attribute dtoToEntity(AttributeDTO dto) {
        Attribute attribute = new Attribute();
        attribute.setAttribute(dto.getName());
        return attribute;
    }

    public AttributeDTO entityToDto(Attribute attribute) {
        AttributeDTO dto = new AttributeDTO();
        dto.setName(attribute.getAttribute());
        return dto;
    }
}