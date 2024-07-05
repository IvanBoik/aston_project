package com.aston.aston_project.controller;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.order.OrderWithAddressProductsAndRecipesDTO;
import com.aston.aston_project.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser("test")
    public void getAllUserOrders_statusOk() throws Exception {

        when(orderService.getAllUserOrders("test")).thenReturn(List.of(new OrderWithAddressProductsAndRecipesDTO()));
        mockMvc.perform(get("/api/v1/orders"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("message").isArray()
                );
        verify(orderService, times(1)).getAllUserOrders(any());
    }

    @Test
    public void getAllUserOrders_statusForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/orders"))
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("message").isString()
                );
        verifyNoInteractions(orderService);
    }

    @Test
    @WithMockUser
    public void createOrder_bodyNotSet_badRequest() throws Exception {
        mockMvc.perform(post("/api/v1/orders"))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("message").isString()
                );
        verifyNoInteractions(orderService);
    }
}
