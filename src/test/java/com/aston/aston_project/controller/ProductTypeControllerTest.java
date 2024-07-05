package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.ProductTypeDTO;
import com.aston.aston_project.service.ProductTypeService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class ProductTypeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductTypeService productTypeService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/products/types";

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_ProductTypeDTO_when_ValidId_Then_StatusOk() throws Exception {

        ProductTypeDTO productTypeDTO = new ProductTypeDTO();
        when(productTypeService.getById(anyLong())).thenReturn(productTypeDTO);
        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productTypeService, times(1)).getById(anyLong());
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

        when(productTypeService.getById(anyLong())).thenThrow(new NotFoundDataException("Product type with id " + anyLong() + " not found"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(
                        status().isBadRequest());
        verify(productTypeService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void given_AllProductTypeDTO_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<ProductTypeDTO> listProductTypeDTO = new ArrayList<>();
        when(productTypeService.getAll()).thenReturn(listProductTypeDTO);

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productTypeService, times(1)).getAll();
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
    void given_AllProductTypeDTO_when_RequestParamProductTypeIsPresent_Then_StatusOk() throws Exception {

        List<ProductTypeDTO> listProductTypeDTO = new ArrayList<>();
        when(productTypeService.findByName(anyString())).thenReturn(listProductTypeDTO);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productTypeService, times(1)).findByName(anyString());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_AddProductTypeInBD_Then_StatusOk() throws Exception {
        ProductTypeDTO dto = new ProductTypeDTO();
        dto.setName("ProductType");
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_AddProductTypeInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(post(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateProductTypeInBD_Then_StatusOk() throws Exception {
        ProductTypeDTO dto = new ProductTypeDTO();
        dto.setName("ProductType");
        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateProductTypeInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_DeleteProductTypeInBD_Then_StatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_DeleteProductTypeInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }
}