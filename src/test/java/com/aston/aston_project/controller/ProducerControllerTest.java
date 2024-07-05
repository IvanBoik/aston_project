package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.service.ProducerService;
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
class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerService producerService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/producers";

    @Test
    @WithMockUser(username = "test")
    void given_ProducerDtoResponse_when_ValidId_Then_StatusOk() throws Exception {

        ProducerDtoResponse producerDtoResponse = new ProducerDtoResponse();
        when(producerService.getById(anyLong())).thenReturn(producerDtoResponse);

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(producerService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_ProducerDtoResponse_when_InvalidId_Then_BadRequest() throws Exception {

        when(producerService.getById(anyLong())).thenThrow(new NotFoundDataException("Producer with id " + anyLong() + " not found"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isBadRequest());
        verify(producerService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProducerResponse_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<ProducerDtoResponse> listProducerDto = new ArrayList<>();
        when(producerService.getAll()).thenReturn(listProducerDto);

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(producerService, times(1)).getAll();
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProducerDtoResponse_when_RequestParamCountryIsPresent_Then_StatusOk() throws Exception {

        List<ProducerDtoResponse> listProducerDto = new ArrayList<>();
        when(producerService.findByNameIgnoreCaseContainingAndCountry(anyString(), anyLong())).thenReturn(listProducerDto);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A")
                        .param("country", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(producerService, times(1)).findByNameIgnoreCaseContainingAndCountry(anyString(), anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProducerDtoResponse_when_RequestParamNameIsPresent_Then_StatusOk() throws Exception {

        List<ProducerDtoResponse> listProducerDto = new ArrayList<>();
        when(producerService.findByNameIgnoreCaseContaining(anyString())).thenReturn(listProducerDto);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(producerService, times(1)).findByNameIgnoreCaseContaining(anyString());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_AddProducerInBD_Then_StatusOk() throws Exception {
        ProducerDtoResponse dto = new ProducerDtoResponse();
        dto.setName("Producer");
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_AddProducerInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(post(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateProducerInBD_Then_StatusOk() throws Exception {
        ProducerDtoResponse dto = new ProducerDtoResponse();
        dto.setName("Producer");
        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateProducerInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_DeleteProducerInBD_Then_StatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_DeleteProducerInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }
}