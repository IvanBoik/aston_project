package com.aston.aston_project.service;

import com.aston.aston_project.dto.CountryDTO;
import com.aston.aston_project.dto.util.CountryDTOMapping;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.repository.CountryRepository;
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
class CountryServiceImplTest {
    @Mock
    CountryRepository countryRepository;
    @Mock
    CountryDTOMapping countryDTOMapping;
    @InjectMocks
    CountryServiceImpl countryService;

    Long id = 100L;

    @Test
    void getById_withValidId() {
        Country country = generateCountry();
        CountryDTO dto = new CountryDTO("test country");

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        when(countryDTOMapping.entityToDto(country)).thenReturn(dto);

        CountryDTO result = countryService.getById(id);

        assertEquals(result, dto);

        verify(countryRepository).findById(id);
        verify(countryDTOMapping).entityToDto(country);
    }

    @Test
    void testGetById_WithNotValidId() {
        when(countryRepository.findById(id)).thenThrow(new NotFoundDataException("Country with id " + id + " not found"));

        assertThatThrownBy(
                () -> countryService.getById(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(countryRepository).findById(id);
    }

    @Test
    void findByCountryName() {
        String namePart = "tes";
        Country country = generateCountry();
        CountryDTO dto = new CountryDTO("test country");

        when(countryRepository.findByCountryIgnoreCaseContaining(namePart)).thenReturn(List.of(country));
        when(countryDTOMapping.entityToDto(country)).thenReturn(dto);

        assertEquals(1, countryService.findByCountryName(namePart).size());

        verify(countryRepository).findByCountryIgnoreCaseContaining(namePart);
        verify(countryDTOMapping).entityToDto(country);
    }

    @Test
    void getAll() {
        Country country = generateCountry();
        CountryDTO dto = new CountryDTO("test country");

        when(countryRepository.findAll()).thenReturn(List.of(country));
        when(countryDTOMapping.entityToDto(country)).thenReturn(dto);

        assertEquals(1, countryService.getAll().size());

        verify(countryRepository).findAll();
        verify(countryDTOMapping).entityToDto(country);
    }

    @Test
    void update_withExistingId() {
        Country country = generateCountry();
        CountryDTO dto = new CountryDTO("some");

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        country.setCountry(dto.getName());

        when(countryRepository.save(country)).thenReturn(country);

        countryService.update(id, dto);

        assertEquals(country.getCountry(), "some");

        verify(countryRepository).findById(id);
        verify(countryRepository).save(country);
    }

    @Test
    void create() {
        Country country = generateCountry();
        CountryDTO dto = new CountryDTO("test country");

        when(countryRepository.save(country)).thenReturn(country);
        when(countryDTOMapping.dtoToEntity(dto)).thenReturn(country);

        countryRepository.save(countryDTOMapping.dtoToEntity(dto));

        verify(countryDTOMapping).dtoToEntity(dto);
        verify(countryRepository, only()).save(country);
    }

    @Test
    void delete() {
        Country country = generateCountry();

        doNothing().when(countryRepository).deleteById(country.getId());
        countryService.delete(country.getId());

        verify(countryRepository).deleteById(country.getId());
    }

    private Country generateCountry() {
        Country country = new Country();
        country.setId(id);
        country.setCountry("test country");
        return country;
    }
}