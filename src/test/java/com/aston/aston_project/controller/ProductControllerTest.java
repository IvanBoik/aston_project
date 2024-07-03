package com.aston.aston_project.controller;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.jwt.JwtUtils;
import com.aston.aston_project.service.ProductService;
import com.aston.aston_project.service.UserService;
import com.aston.aston_project.util.ExceptionController;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserService userService;
    @MockBean
    private ExceptionController exceptionController;

    @Test
    void given_ProductDtoFullResponse_when_ValidId_Then_StatusOk() throws Exception {

        ProductDtoFullResponse productDtoFullResponse = new ProductDtoFullResponse();
        when(productService.getById(anyLong())).thenReturn(productDtoFullResponse);

        mockMvc.perform(get("/api/v1/products/1")
                        .with(user("test")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_Exception_when_InvalidId_Then_BadRequest() throws Exception {

        when(productService.getById(anyLong())).thenThrow(new NotFoundDataException("Product not found"));

        mockMvc.perform(get("/api/v1/products/1")
                        .with(user("test")))
                .andExpect(
                        status().isBadRequest());
    }

    @Test
    void given_AllProductDtoShort_when_NotRequestParam_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.getAll()).thenReturn(listProductDto);

        mockMvc.perform(get("/api/v1/products")
                        .with(user("test")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void given_AllProductDtoShort_when_RequestParamProducerIsPresent_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.findByNameIgnoreCaseContainingAndProducer(anyString(), anyLong())).thenReturn(listProductDto);

        mockMvc.perform(get("/api/v1/products?name=A&producer=1")
                        .with(user("test")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void given_AllProductDtoShort_when_RequestParamRecipeIsPresent_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.findByNameIgnoreCaseContainingAndIsPrescriptionRequired(anyString(), anyInt())).thenReturn(listProductDto);

        mockMvc.perform(get("/api/v1/products?name=A&recipe=1")
                        .with(user("test")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void given_AllProductDtoShort_when_RequestParamNameIsPresent_Then_StatusOk() throws Exception {

        List<ProductDtoShort> listProductDto = new ArrayList<>();
        when(productService.findByNameIgnoreCaseContaining(anyString())).thenReturn(listProductDto);

        mockMvc.perform(get("/api/v1/products?name=A")
                        .with(user("test")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void delete() {
    }
}
