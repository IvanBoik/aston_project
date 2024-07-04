package com.aston.aston_project.service;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@Sql("classpath:manager_script.sql")
public class ManagerControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManagerService service;

    @Autowired
    private OrderRepository repository;
    @Test
    public void getAllOrders_returnsValidData() throws Exception {
        mockMvc.perform(
                get("/managers/v1/orders")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

        assertNotEquals(0,service.getAllOrders().size());
    }

    @Test
    @WithAnonymousUser
    public void getAllOrders_forbidden() throws Exception {
        mockMvc.perform(get("/managers/v1/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllOrders_methodNotAllowed() throws Exception{
        mockMvc.perform(post("/managers/v1/orders")
                        .with(user("test").roles("MANAGER")))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getAllSuspiciousOrders_returnsValidData() throws Exception {
        mockMvc.perform(
                        get("/managers/v1/orders/suspicious")
                                .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

        assertNotEquals(0,service.getAllOrders().size());
    }

    @Test
    @WithAnonymousUser
    public void getAllSuspiciousOrders_forbidden() throws Exception {
        mockMvc.perform(get("/managers/v1/orders/suspicious"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllSuspiciousOrders_methodNotAllowed() throws Exception{
        mockMvc.perform(post("/managers/v1/orders/suspicious")
                        .with(user("test").roles("MANAGER")))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void switchStatus_switchingStatus() throws Exception {
        mockMvc.perform(patch("/managers/v1/orders/1")
                        .param("status","DRAFT")
                .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void switchStatus_orderNotFound() throws Exception {
        mockMvc.perform(patch("/managers/v1/orders/9999")
                        .param("status","DRAFT")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void switchStatus_orderNotPresent() throws Exception {
        mockMvc.perform(patch("/managers/v1/orders/")
                        .param("status","DRAFT")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void switchStatus_statusNotPresent() throws Exception {
        mockMvc.perform(patch("/managers/v1/orders/1")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void switchStatus_statusNotValid() throws Exception {
        mockMvc.perform(patch("/managers/v1/orders/1")
                        .param("status","incorrect")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void switchStatus_methodNotAllowed() throws Exception {
        mockMvc.perform(get("/managers/v1/orders/1")
                        .param("status","DRAFT")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isMethodNotAllowed(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    @WithAnonymousUser
    public void switchStatus_forbidden() throws Exception {
        mockMvc.perform(get("/managers/v1/orders/1")
                        .param("status","DRAFT"))
                .andExpectAll(
                        status().isForbidden(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void getAllOrdersWithRecipe_returnsValidData() throws Exception{
            mockMvc.perform(
                            get("/managers/v1/orders/recipes")
                                    .with(user("test").roles("MANAGER")))
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    );

            assertNotEquals(0,service.getRecipeOrders().size());
    }

    @Test
    @WithAnonymousUser
    public void getAllOrdersWithRecipe_forbidden() throws Exception {
        mockMvc.perform(get("/managers/v1/orders/recipes"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllOrdersWithRecipe_methodNotAllowed() throws Exception{
        mockMvc.perform(post("/managers/v1/orders/recipes")
                        .with(user("test").roles("MANAGER")))
                .andExpect(status().isMethodNotAllowed());
    }

    @RetryingTest(3)
    public void checkRecipe_isValid() throws Exception {
        mockMvc.perform(get("/managers/v1/recipes/1/check")
                .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
        assertEquals(true,service.checkRecipe(1L).isValid());
    }

    @RetryingTest(30)
    public void checkRecipe_isNotValidRandomly() throws Exception {
        mockMvc.perform(get("/managers/v1/recipes/1/check")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
        assertEquals(false,service.checkRecipe(1L).isValid());
    }

    @Test
    public void checkRecipe_isNotValid() throws Exception {
        mockMvc.perform(get("/managers/v1/recipes/2/check")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
        assertEquals(false,service.checkRecipe(2L).isValid());
    }

    @Test
    public void checkRecipe_isNotFound() throws Exception {
        mockMvc.perform(get("/managers/v1/recipes/999/check")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isBadRequest());
    }

    @Test
    public void checkRecipe_methodNotAllowed() throws Exception {
        mockMvc.perform(post("/managers/v1/recipes/1/check")
                        .with(user("test").roles("MANAGER")))
                .andExpectAll(
                        status().isMethodNotAllowed(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );;
    }

    @Test
    public void checkRecipe_forbidden() throws Exception {
        mockMvc.perform(post("/managers/v1/recipes/1/check"))
                .andExpectAll(
                        status().isForbidden(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }
}
