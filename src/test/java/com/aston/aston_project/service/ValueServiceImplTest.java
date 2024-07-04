package com.aston.aston_project.service;

import com.aston.aston_project.dto.ValueDTO;
import com.aston.aston_project.dto.util.ValueDTOMapping;
import com.aston.aston_project.entity.Value;
import com.aston.aston_project.repository.ValueRepository;
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
class ValueServiceImplTest {
    @Mock
    ValueRepository valueRepository;
    @Mock
    ValueDTOMapping valueDTOMapping;
    @InjectMocks
    ValueServiceImpl valueService;
    Long id = 100L;

    @Test
    void getById_withValidId() {
        Value value = new Value(id);
        ValueDTO dto = new ValueDTO("test value");

        when(valueRepository.findById(id)).thenReturn(Optional.of(value));
        when(valueDTOMapping.entityToDto(value)).thenReturn(dto);

        ValueDTO result = valueService.getById(id);

        assertEquals(result, dto);

        verify(valueRepository).findById(id);
        verify(valueDTOMapping).entityToDto(value);
    }

    @Test
    void testGetById_WithNotValidId() {
        when(valueRepository.findById(id)).thenThrow(new NotFoundDataException("Value with id " + id + " not found"));

        assertThatThrownBy(
                () -> valueService.getById(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(valueRepository).findById(id);
    }

    @Test
    void findByName() {
        String namePart = "tes";
        Value value = new Value(id);
        ValueDTO dto = new ValueDTO("test value");

        when(valueRepository.findByValueIgnoreCaseContaining(namePart)).thenReturn(List.of(value));
        when(valueDTOMapping.entityToDto(value)).thenReturn(dto);

        assertEquals(1, valueService.findByName(namePart).size());

        verify(valueRepository).findByValueIgnoreCaseContaining(namePart);
        verify(valueDTOMapping).entityToDto(value);
    }

    @Test
    void getAll() {
        Value value = new Value(id);
        ValueDTO dto = new ValueDTO("test value");

        when(valueRepository.findAll()).thenReturn(List.of(value));
        when(valueDTOMapping.entityToDto(value)).thenReturn(dto);

        assertEquals(1, valueService.getAll().size());

        verify(valueRepository).findAll();
        verify(valueDTOMapping).entityToDto(value);
    }

    @Test
    void update_withExistingId() {
        Value value = new Value(id);
        ValueDTO dto = new ValueDTO("some");

        when(valueRepository.findById(id)).thenReturn(Optional.of(value));
        value.setValue(dto.getName());

        when(valueRepository.save(value)).thenReturn(value);

        valueService.update(id, dto);

        assertEquals(value.getValue(), "some");

        verify(valueRepository).findById(id);
        verify(valueRepository).save(value);
    }

    @Test
    void create() {
        Value value = new Value(id);
        ValueDTO dto = new ValueDTO("some");

        when(valueRepository.save(value)).thenReturn(value);
        when(valueDTOMapping.dtoToEntity(dto)).thenReturn(value);

        valueRepository.save(valueDTOMapping.dtoToEntity(dto));

        verify(valueDTOMapping).dtoToEntity(dto);
        verify(valueRepository, only()).save(value);
    }

    @Test
    void delete() {
        Value value = new Value(id);

        doNothing().when(valueRepository).deleteById(value.getId());
        valueService.delete(value.getId());

        verify(valueRepository).deleteById(value.getId());
    }
}