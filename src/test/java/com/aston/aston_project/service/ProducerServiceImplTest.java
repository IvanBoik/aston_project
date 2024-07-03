package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.dto.util.ProducerDtoMapping;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.repository.CountryRepository;
import com.aston.aston_project.repository.ProducerRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerServiceImplTest {
    @Mock
    ProducerRepository producerRepository;
    @Mock
    ProducerDtoMapping producerDtoMapping;
    @Mock
    CountryRepository countryRepository;
    @InjectMocks
    ProducerServiceImpl producerService;
    Long id = 100L;

    @Test
    void getById() {
        Producer producer = new Producer(id);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", "some country");

        when(producerRepository.findById(id)).thenReturn(Optional.of(producer));
        when(producerDtoMapping.entityToDto(producer)).thenReturn(dto);

        ProducerDtoResponse result = producerService.getById(id);

        assertEquals(result, dto);

        verify(producerRepository).findById(id);
        verify(producerDtoMapping).entityToDto(producer);
    }

    @Test
    void testGetById_WithNotValidId() {
        when(producerRepository.findById(id)).thenThrow(new NotFoundDataException("Producer with id " + id + " not found"));

        assertThatThrownBy(
                () -> producerService.getById(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(producerRepository).findById(id);
    }

    @Test
    void getAll() {
        Producer producer = new Producer(id);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", "some country");

        when(producerRepository.findAll()).thenReturn(List.of(producer));
        when(producerDtoMapping.entityToDto(producer)).thenReturn(dto);

        assertEquals(1, producerService.getAll().size());

        verify(producerRepository).findAll();
        verify(producerDtoMapping).entityToDto(producer);
    }

    @Test
    void findByNameIgnoreCaseContaining() {
        String namePart = "tes";
        Producer producer = new Producer(id);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", "some country");

        when(producerRepository.findByNameIgnoreCaseContaining(namePart)).thenReturn(List.of(producer));
        when(producerDtoMapping.entityToDto(producer)).thenReturn(dto);

        assertEquals(1, producerService.findByNameIgnoreCaseContaining(namePart).size());

        verify(producerRepository).findByNameIgnoreCaseContaining(namePart);
        verify(producerDtoMapping).entityToDto(producer);
    }

    @Test
    void findByCountry_withValidCountry() {
        Country country = new Country();
        country.setId(id);
        country.setCountry("test country");
        Producer producer = new Producer(id);
        producer.setCountry(country);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", country.getCountry());

        when(producerRepository.findByCountry(country)).thenReturn(List.of(producer));
        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        when(producerDtoMapping.entityToDto(producer)).thenReturn(dto);

        assertEquals(1, producerService.findByCountry(id).size());

        verify(producerRepository).findByCountry(country);
        verify(countryRepository).findById(id);
        verify(producerDtoMapping).entityToDto(producer);
    }

    @Test
    void findByCountry_WithNotValidCountry() {
        when(countryRepository.findById(id)).thenThrow(new NotFoundDataException("Producers from this country not found"));

        assertThatThrownBy(
                () -> producerService.findByCountry(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(countryRepository).findById(id);
    }

    @Test
    void findByNameIgnoreCaseContainingAndCountry_withValidCountry() {
        String namePart = "tes";
        Country country = new Country();
        country.setId(id);
        country.setCountry("test country");
        Producer producer = new Producer(id);
        producer.setCountry(country);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", country.getCountry());

        when(producerRepository.findByNameIgnoreCaseContainingAndCountry(namePart, country)).thenReturn(List.of(producer));
        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        when(producerDtoMapping.entityToDto(producer)).thenReturn(dto);

        assertEquals(1, producerService.findByNameIgnoreCaseContainingAndCountry(namePart, id).size());

        verify(countryRepository).findById(id);
        verify(producerRepository).findByNameIgnoreCaseContainingAndCountry(namePart, country);
        verify(producerDtoMapping).entityToDto(producer);
    }

    @Test
    void create() {
        Country country = new Country();
        country.setId(id);
        country.setCountry("test country");
        Producer producer = new Producer(id);
        producer.setCountry(country);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", country.getCountry());

        when(producerRepository.save(producer)).thenReturn(producer);
        when(producerDtoMapping.dtoToEntity(dto, country)).thenReturn(producer);

        producerRepository.save(producerDtoMapping.dtoToEntity(dto, country));

        verify(producerDtoMapping).dtoToEntity(dto, country);
        verify(producerRepository, only()).save(producer);
    }

    @Test
    void update_withExistingId() {
        Country country = new Country();
        country.setId(id);
        country.setCountry("test country");
        Producer producer = new Producer(id);
        ProducerDtoResponse dto = new ProducerDtoResponse("Test producer", country.getCountry());

        when(producerRepository.findById(id)).thenReturn(Optional.of(producer));
        producer.setName(dto.getName());
        producer.setCountry(country);

        when(producerRepository.save(producer)).thenReturn(producer);

        producerService.update(id, dto);

        assertTrue(producer.getCountry().getCountry().contains("test country"));

        verify(producerRepository).findById(id);
        verify(producerRepository).save(producer);
    }

    @Test
    void delete() {
        Producer producer = new Producer(id);

        doNothing().when(producerRepository).deleteById(producer.getId());
        producerService.delete(producer.getId());

        verify(producerRepository).deleteById(producer.getId());
    }
}