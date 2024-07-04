package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductTypeDTO;
import com.aston.aston_project.dto.util.ProductTypeDTOMapping;
import com.aston.aston_project.entity.ProductType;
import com.aston.aston_project.repository.ProductTypeRepository;
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
class ProductTypeServiceImplTest {
    @Mock
    ProductTypeRepository productTypeRepository;
    @Mock
    ProductTypeDTOMapping productTypeDTOMapping;
    @InjectMocks
    ProductTypeServiceImpl productTypeService;
    Long id = 100L;

    @Test
    void getById_withValidId() {
        ProductType type = new ProductType(id);
        ProductTypeDTO dto = new ProductTypeDTO("test type");

        when(productTypeRepository.findById(id)).thenReturn(Optional.of(type));
        when(productTypeDTOMapping.entityToDto(type)).thenReturn(dto);

        ProductTypeDTO result = productTypeService.getById(id);

        assertEquals(result, dto);

        verify(productTypeRepository).findById(id);
        verify(productTypeDTOMapping).entityToDto(type);
    }

    @Test
    void testGetById_WithNotValidId() {
        when(productTypeRepository.findById(id)).thenThrow(new NotFoundDataException("Product type with id " + id + " not found"));

        assertThatThrownBy(
                () -> productTypeService.getById(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(productTypeRepository).findById(id);
    }

    @Test
    void findByName() {
        String namePart = "tes";
        ProductType type = new ProductType(id);
        ProductTypeDTO dto = new ProductTypeDTO("test type");

        when(productTypeRepository.findByNameIgnoreCaseContaining(namePart)).thenReturn(List.of(type));
        when(productTypeDTOMapping.entityToDto(type)).thenReturn(dto);

        assertEquals(1, productTypeService.findByName(namePart).size());

        verify(productTypeRepository).findByNameIgnoreCaseContaining(namePart);
        verify(productTypeDTOMapping).entityToDto(type);
    }

    @Test
    void getAll() {
        ProductType type = new ProductType(id);
        ProductTypeDTO dto = new ProductTypeDTO("test type");

        when(productTypeRepository.findAll()).thenReturn(List.of(type));
        when(productTypeDTOMapping.entityToDto(type)).thenReturn(dto);

        assertEquals(1, productTypeService.getAll().size());

        verify(productTypeRepository).findAll();
        verify(productTypeDTOMapping).entityToDto(type);
    }

    @Test
    void update_withExistingId() {
        ProductType type = new ProductType(id);
        ProductTypeDTO dto = new ProductTypeDTO("some");

        when(productTypeRepository.findById(id)).thenReturn(Optional.of(type));
        type.setName(dto.getName());

        when(productTypeRepository.save(type)).thenReturn(type);

        productTypeService.update(id, dto);

        assertEquals(type.getName(), "some");

        verify(productTypeRepository).findById(id);
        verify(productTypeRepository).save(type);
    }

    @Test
    void create() {
        ProductType type = new ProductType(id);
        ProductTypeDTO dto = new ProductTypeDTO("test type");

        when(productTypeRepository.save(type)).thenReturn(type);
        when(productTypeDTOMapping.dtoToEntity(dto)).thenReturn(type);

        productTypeRepository.save(productTypeDTOMapping.dtoToEntity(dto));

        verify(productTypeDTOMapping).dtoToEntity(dto);
        verify(productTypeRepository, only()).save(type);
    }

    @Test
    void delete() {
        ProductType type = new ProductType(id);

        doNothing().when(productTypeRepository).deleteById(type.getId());
        productTypeService.delete(type.getId());

        verify(productTypeRepository).deleteById(type.getId());
    }
}