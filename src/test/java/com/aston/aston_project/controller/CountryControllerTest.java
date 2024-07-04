package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.CountryDTO;
import com.aston.aston_project.service.CountryService;
import com.aston.aston_project.util.exception.NotFoundDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CountryService countryService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/countries";

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_CountryDTO_when_ValidId_Then_StatusOk() throws Exception {

        CountryDTO countryDTO = new CountryDTO();
        when(countryService.getById(anyLong())).thenReturn(countryDTO);
        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(countryService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_Exception_when_InvalidRoleUserForGetById_Then_Forbidden() throws Exception {

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_Exception_when_InvalidId_Then_BadRequest() throws Exception {

        when(countryService.getById(anyLong())).thenThrow(new NotFoundDataException("Country with id " + anyLong() + " not found"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(
                        status().isBadRequest());
        verify(countryService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_AllCountryDTO_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<CountryDTO> listCountryDto = new ArrayList<>();
        when(countryService.getAll()).thenReturn(listCountryDto);

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(countryService, times(1)).getAll();
    }

    @Test
    @WithMockUser(username = "test")
    void given_Exception_when_InvalidRoleUserForGetAll_Then_Forbidden() throws Exception {

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_AllCountryDTO_when_RequestParamCountryIsPresent_Then_StatusOk() throws Exception {

        List<CountryDTO> listCountryDto = new ArrayList<>();
        when(countryService.findByCountryName(anyString())).thenReturn(listCountryDto);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(countryService, times(1)).findByCountryName(anyString());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_AddCountryInBD_Then_StatusOk() throws Exception {
        CountryDTO dto = new CountryDTO();
        dto.setName("Country");
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_AddCountryInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(post(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateCountryInBD_Then_StatusOk() throws Exception {
        CountryDTO dto = new CountryDTO();
        dto.setName("Country");
        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateCountryInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_DeleteCountryInBD_Then_StatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_DeleteCountryInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }
}