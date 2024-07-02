package com.aston.aston_project.chain;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.controller.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public class OrderControllerIT {

    @Autowired
    private OrderController controller;

    @Autowired
    private MockMvc mockMvc;


}
