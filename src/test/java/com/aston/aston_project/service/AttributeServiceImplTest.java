package com.aston.aston_project.service;

import com.aston.aston_project.dto.AttributeDTO;
import com.aston.aston_project.dto.util.AttributeDTOMapping;
import com.aston.aston_project.entity.Attribute;
import com.aston.aston_project.repository.AttributeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttributeServiceImplTest {
    @Mock
    AttributeRepository attributeRepository;
    @Mock
    AttributeDTOMapping attributeDTOMapping;
    @InjectMocks
    AttributeServiceImpl attributeService;
    Long id = 100L;

    @Test
    void getById_withValidId() {
        Attribute attribute = new Attribute(id);
        AttributeDTO dto = new AttributeDTO("test attribute");

        when(attributeRepository.findById(id)).thenReturn(Optional.of(attribute));
        when(attributeDTOMapping.entityToDto(attribute)).thenReturn(dto);

        AttributeDTO result = attributeService.getById(id);

        assertEquals(result, dto);

        verify(attributeRepository).findById(id);
        verify(attributeDTOMapping).entityToDto(attribute);
    }

    @Test
    void testGetById_WithNotValidId() {
        when(attributeRepository.findById(id)).thenThrow(new NotFoundDataException("Product attribute with id " + id + " not found"));

        assertThatThrownBy(
                () -> attributeService.getById(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(attributeRepository).findById(id);
    }

    @Test
    void findByName() {
        String namePart = "tes";
        Attribute attribute = new Attribute(id);
        AttributeDTO dto = new AttributeDTO("test attribute");

        when(attributeRepository.findByAttributeIgnoreCaseContaining(namePart)).thenReturn(List.of(attribute));
        when(attributeDTOMapping.entityToDto(attribute)).thenReturn(dto);

        assertEquals(1, attributeService.findByName(namePart).size());

        verify(attributeRepository).findByAttributeIgnoreCaseContaining(namePart);
        verify(attributeDTOMapping).entityToDto(attribute);
    }

    @Test
    void getAll() {
        Attribute attribute = new Attribute(id);
        AttributeDTO dto = new AttributeDTO("test attribute");

        when(attributeRepository.findAll()).thenReturn(List.of(attribute));
        when(attributeDTOMapping.entityToDto(attribute)).thenReturn(dto);

        assertEquals(1, attributeService.getAll().size());

        verify(attributeRepository).findAll();
        verify(attributeDTOMapping).entityToDto(attribute);
    }

    @Test
    void update_withExistingId() {
        Attribute attribute = new Attribute(id);
        AttributeDTO dto = new AttributeDTO("some");

        when(attributeRepository.findById(id)).thenReturn(Optional.of(attribute));
        attribute.setAttribute(dto.getName());

        when(attributeRepository.save(attribute)).thenReturn(attribute);

        attributeService.update(id, dto);

        assertEquals(attribute.getAttribute(), "some");

        verify(attributeRepository).findById(id);
        verify(attributeRepository).save(attribute);
    }

    @Test
    void create() {
        Attribute attribute = new Attribute(id);
        AttributeDTO dto = new AttributeDTO("test attribute");

        when(attributeRepository.save(attribute)).thenReturn(attribute);
        when(attributeDTOMapping.dtoToEntity(dto)).thenReturn(attribute);

        attributeRepository.save(attributeDTOMapping.dtoToEntity(dto));

        verify(attributeDTOMapping).dtoToEntity(dto);
        verify(attributeRepository, only()).save(attribute);
    }

    @Test
    void delete() {
        Attribute attribute = new Attribute(id);

        doNothing().when(attributeRepository).deleteById(attribute.getId());
        attributeService.delete(attribute.getId());

        verify(attributeRepository).deleteById(attribute.getId());
    }
}