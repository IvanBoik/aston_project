package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.AttributeDTO;
import com.aston.aston_project.service.AttributeService;
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
class AttributeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AttributeService attributeService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/products/attributes";

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_AttributeDTO_when_ValidId_Then_StatusOk() throws Exception {

        AttributeDTO attributeDTO = new AttributeDTO();
        when(attributeService.getById(anyLong())).thenReturn(attributeDTO);
        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(attributeService, times(1)).getById(anyLong());

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

        when(attributeService.getById(anyLong())).thenThrow(new NotFoundDataException("Product attribute with id " + anyLong() + " not found"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(
                        status().isBadRequest());
        verify(attributeService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_AllAttributeDTO_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<AttributeDTO> listAttributeDTO = new ArrayList<>();
        when(attributeService.getAll()).thenReturn(listAttributeDTO);

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(attributeService, times(1)).getAll();
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
    void given_AllAttributeDTO_when_RequestParamAttributeIsPresent_Then_StatusOk() throws Exception {

        List<AttributeDTO> listAttributeDTO = new ArrayList<>();
        when(attributeService.findByName(anyString())).thenReturn(listAttributeDTO);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(attributeService, times(1)).findByName(anyString());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_AddAttributeInBD_Then_StatusOk() throws Exception {
        AttributeDTO dto = new AttributeDTO();
        dto.setName("Attribute");
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_AddAttributeInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(post(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateAttributeInBD_Then_StatusOk() throws Exception {
        AttributeDTO dto = new AttributeDTO();
        dto.setName("Attribute");
        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateAttributeInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_DeleteAttributeInBD_Then_StatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_DeleteAttributeInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }
}