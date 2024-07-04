package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.ValueDTO;
import com.aston.aston_project.service.ValueService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class ValueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ValueService valueService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/products/values";

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_ValueDTO_when_ValidId_Then_StatusOk() throws Exception {

        ValueDTO ValueDTO = new ValueDTO();
        when(valueService.getById(anyLong())).thenReturn(ValueDTO);
        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(valueService, times(1)).getById(anyLong());
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

        when(valueService.getById(anyLong())).thenThrow(new NotFoundDataException("Value with id " + anyLong() + " not found"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(
                        status().isBadRequest());
        verify(valueService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_AllValueDTO_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<ValueDTO> listValueDTO = new ArrayList<>();
        when(valueService.getAll()).thenReturn(listValueDTO);

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(valueService, times(1)).getAll();
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
    void given_AllValueDTO_when_RequestParamValueIsPresent_Then_StatusOk() throws Exception {

        List<ValueDTO> listValueDTO = new ArrayList<>();
        when(valueService.findByName(anyString())).thenReturn(listValueDTO);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(valueService, times(1)).findByName(anyString());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_AddValueInBD_Then_StatusOk() throws Exception {
        ValueDTO dto = new ValueDTO();
        dto.setName("Value");
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_AddValueInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(post(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateValueInBD_Then_StatusOk() throws Exception {
        ValueDTO dto = new ValueDTO();
        dto.setName("Value");
        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateValueInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_DeleteValueInBD_Then_StatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_DeleteValueInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }
}