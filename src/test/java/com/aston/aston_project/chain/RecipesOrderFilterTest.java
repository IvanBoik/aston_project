package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.ProductList;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.util.exception.IncorrectDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipesOrderFilterTest {
    @Mock
    private OrderChain orderChain;
    @InjectMocks
    private RecipesOrderFilter orderFilter;

    @Mock
    private Order orderConfiguredBefore;

    @BeforeEach
    public void init(){
        Queue<OrderFilter> orderFilterQueue = new ArrayDeque<>();
        orderFilterQueue.add(orderFilter);
        orderChain = new OrderChain(orderFilterQueue);
        Product withRecipe = Product.builder()
                .id(1L)
                .name("Семитикон")
                .price(BigDecimal.valueOf(1000.00))
                .isPrescriptionRequired(true)
                .build();
        Product withoutRecipe = Product.builder()
                .id(2L)
                .name("Миг")
                .price(BigDecimal.valueOf(499.00))
                .isPrescriptionRequired(false)
                .build();
        List<ProductList> list = Stream.of(withRecipe, withoutRecipe).map(p -> ProductList.builder().product(p).build()).toList();
        orderConfiguredBefore = Order.builder()
                .productList(list)
                .recipeList(new ArrayList<>())
                .build();
    }
    @Test
    public void process_whenTypeIsDelivery_throwsIncorrectDataException(){
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.DELIVERY)
                .paymentType(OrderPaymentEnum.CASH)
                .pharmacyId(1L)
                .products(List.of(
                        ProductRequestDto.builder()
                                .id(1L)
                                .recipe("01Д1234567890")
                                .count(100)
                                .build()
                ))
                .addressId(1L)
                .build();
        assertThrows(IncorrectDataException.class,()->orderChain.doFilter(User.builder().build(), request));
    }

    @Test
    public void process_settingEmptyList(){
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.PICKUP)
                .paymentType(OrderPaymentEnum.CASH)
                .pharmacyId(1L)
                .products(List.of(
                        ProductRequestDto.builder()
                                .id(2L)
                                .recipe("01Д1234567890")
                                .count(100)
                                .build()
                ))
                .addressId(1L)
                .build();
        Order order = orderChain.doFilter(User.builder().build(),orderConfiguredBefore, request,orderChain);
        assertTrue(order.getRecipeList().isEmpty());
    }

    @Test
    public void process_settingNotEmptyList(){
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.PICKUP)
                .paymentType(OrderPaymentEnum.CASH)
                .pharmacyId(1L)
                .products(List.of(
                        ProductRequestDto.builder()
                                .id(1L)
                                .recipe("01Д1234567890")
                                .count(100)
                                .build()
                ))
                .addressId(1L)
                .build();
        Order order = orderChain.doFilter(User.builder().build(),orderConfiguredBefore, request,orderChain);
        assertFalse(order.getRecipeList().isEmpty());
    }

    @Test
    public void process_settingEmptyListWhenProductNotPresent(){
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.PICKUP)
                .paymentType(OrderPaymentEnum.CASH)
                .pharmacyId(1L)
                .products(List.of(
                        ProductRequestDto.builder()
                                .id(3L)
                                .recipe("01Д1234567890")
                                .count(100)
                                .build()
                ))
                .addressId(1L)
                .build();
        Order order = orderChain.doFilter(User.builder().build(),orderConfiguredBefore, request,orderChain);
        assertTrue(order.getRecipeList().isEmpty());
    }






}