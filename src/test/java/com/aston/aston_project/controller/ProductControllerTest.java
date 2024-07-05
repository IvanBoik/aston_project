package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.service.ProductService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String baseUrl = "/api/v1/products";

    @Test
    @WithMockUser(username = "test")
    void given_ProductDtoFullResponse_when_ValidId_Then_StatusOk() throws Exception {

        ProductDtoFullResponse productDtoFullResponse = new ProductDtoFullResponse();
        when(productService.getById(anyLong())).thenReturn(productDtoFullResponse);

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_Exception_when_InvalidId_Then_BadRequest() throws Exception {

        when(productService.getById(anyLong())).thenThrow(new NotFoundDataException("Product not found"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(
                        status().isBadRequest());
        verify(productService, times(1)).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProductDtoShort_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.getAll()).thenReturn(listProductDto);

        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(1)).getAll();
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProductDtoShort_when_RequestParamProducerIsPresent_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.findByNameIgnoreCaseContainingAndProducer(anyString(), anyLong())).thenReturn(listProductDto);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A")
                        .param("producer", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(1)).findByNameIgnoreCaseContainingAndProducer(anyString(), anyLong());
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProductDtoShort_when_RequestParamRecipeIsPresent_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.findByNameIgnoreCaseContainingAndIsPrescriptionRequired(anyString(), anyInt())).thenReturn(listProductDto);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A")
                        .param("recipe", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(1)).findByNameIgnoreCaseContainingAndIsPrescriptionRequired(anyString(), anyInt());
    }

    @Test
    @WithMockUser(username = "test")
    void given_AllProductDtoShort_when_RequestParamNameIsPresent_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.findByNameIgnoreCaseContaining(anyString())).thenReturn(listProductDto);

        mockMvc.perform(get(baseUrl)
                        .param("name", "A"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(1)).findByNameIgnoreCaseContaining(anyString());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_AddProductInBD_Then_StatusOk() throws Exception {
        ProductRequest dto = new ProductRequest();
        dto.setName("Product");
        dto.setPrice(BigDecimal.TEN);
        dto.setIsPrescriptionRequired(Boolean.TRUE);
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_AddProductInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(post(baseUrl))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateProductInBD_Then_StatusOk() throws Exception {
        ProductRequest dto = new ProductRequest();
        dto.setName("Product");
        dto.setPrice(BigDecimal.TEN);
        dto.setIsPrescriptionRequired(Boolean.TRUE);
        mockMvc.perform(put(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateProductInBD_Then_Forbidden() throws Exception {

        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_UpdateRecipeInBD_Then_StatusOk() throws Exception {

        mockMvc.perform(put(baseUrl)
                        .param("id", "1")
                        .param("isRecipe", "True"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_UpdateRecipeInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(put(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void when_DeleteProductInBD_Then_StatusOk() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isOk());
    }

    @Test
    @WithMockUser(username = "test")
    void when_DeleteProductInBD_Then_Forbidden() throws Exception {
        mockMvc.perform(delete(baseUrl + "/1"))
                .andExpectAll(
                        status().isForbidden());
    }
}