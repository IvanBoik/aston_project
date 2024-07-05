package com.aston.aston_project.service;

import com.aston.aston_project.config.TestConfig;
import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.order.OrderWithAddressProductsAndRecipesDTO;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.OrderRepository;
import com.aston.aston_project.repository.UserRepository;
import com.aston.aston_project.util.exception.BalanceProcessingException;
import com.aston.aston_project.util.exception.IncorrectDataException;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = TestConfig.class)
@Sql(value = "classpath:order_script.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    @Order(1)
    public void getAllUserOrder_returnsEmptyList(){
        List<OrderWithAddressProductsAndRecipesDTO> response = orderService.getAllUserOrders("test@test.tt");
        Assertions.assertEquals(0, response.size());
    }


    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "correct")
    @Order(2)
    public void createOrder_correctlyInsertValue(OrderCreateRequestDto dto){
        OrderWithAddressProductsAndRecipesDTO order = orderService.createOrder("test@test.tt", dto);
        User user = userRepository.findUserByEmail("test@test.tt").get();
        assertEquals(OrderTypeEnum.DELIVERY,order.getType());
        assertEquals(OrderStatusEnum.IN_PROCESS,order.getStatus());
        assertEquals(order.getAddress().getId(),user.getAddresses().get(0).getId());
        assertTrue(BigDecimal.valueOf(0).compareTo(user.getBalance())==0);
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "correct")
    @Order(3)
    public void createOrder_incorrectBalance_throwsException(OrderCreateRequestDto dto){
        assertThrows(BalanceProcessingException.class,()->orderService.createOrder("test@test.tt", dto));
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "incorrect_user_address")
    @Order(4)
    public void createOrder_incorrectAddress_throwsException(OrderCreateRequestDto dto){
        assertThrows(NotFoundDataException.class,()->orderService.createOrder("test@test.tt", dto));
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "products_not_present_in_pharmacy")
    @Order(5)
    public void createOrder_incorrectProductInPharmacy_throwsException(OrderCreateRequestDto dto){
        assertThrows(NotFoundDataException.class,()->orderService.createOrder("test@test.tt",dto));
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "recipe_delivery")
    @Order(5)
    public void createOrder_deliveryRecipeProducts_throwsException(OrderCreateRequestDto dto){
        assertThrows(IncorrectDataException.class,()->orderService.createOrder("test@test.tt",dto));
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "recipe_not_set")
    @Order(5)
    public void createOrder_recipeNotSet_throwsException(OrderCreateRequestDto dto){
        assertThrows(IncorrectDataException.class,()->orderService.createOrder("test@test.tt",dto));
    }

    @ParameterizedTest
    @JsonClasspathSource(value = "order.json",data = "product_count_much_bigger")
    @Order(5)
    public void createOrder_countBiggerThanExist_throwsException(OrderCreateRequestDto dto){
        assertThrows(IncorrectDataException.class,()->orderService.createOrder("test@test.tt",dto));
    }




}
