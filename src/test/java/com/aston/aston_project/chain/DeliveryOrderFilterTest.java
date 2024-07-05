package com.aston.aston_project.chain;

import com.aston.aston_project.api.delivery.client.MockDeliveryResponse;
import com.aston.aston_project.api.delivery.util.DeliveryResponse;
import com.aston.aston_project.api.delivery.util.DeliveryService;
import com.aston.aston_project.api.delivery.util.DeliveryStatus;
import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.OrderStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DeliveryOrderFilterTest {

    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private DeliveryService service;

    @InjectMocks
    private DeliveryOrderFilter orderFilter;

    @Mock
    private OrderChain orderChain;
    @Mock
    private Order orderConfiguredBefore;

    @BeforeEach
    public void init() {
        CompletableFuture<DeliveryResponse> future = getCompletableFuture();
        orderChain = getOrderChain(orderFilter);
        Product withRecipe = getProductWithRecipe();
        Product withoutRecipe = getWithoutRecipe();
        ProductList withRecipeProductList = getProductList(withRecipe, 1000);
        ProductList withoutRecipeProductList = getProductList(withoutRecipe, 1);
        orderConfiguredBefore = getOrder(withRecipeProductList, withoutRecipeProductList);
        when(service.createDelivery(any())).thenReturn(future);
        when(orderStatusRepository.findByStatus(any())).thenReturn(Optional.empty());
    }

    @Test
    public void process_returnsInProcess() {
        Order order = Order.builder()
                .type(OrderType.builder().name(OrderTypeEnum.DELIVERY).build()).build();
        Address userAddress = Address.builder()
                .id(1L)
                .city("Санкт-Петербург")
                .street("Гагарина")
                .house("4")
                .room((short) 4)
                .build();

        User user = User.builder()
                .name("Иван")
                .addresses(List.of(userAddress))
                .phone("+7888 888 88 88").build();
        OrderCreateRequestDto request = OrderCreateRequestDto.builder().type(OrderTypeEnum.DELIVERY).build();

        Order response = orderChain.doFilter(user, order, request);
        assertEquals(OrderStatusEnum.IN_PROCESS,response.getStatus().getStatus());
    }

    @Test
    public void process_returnsDraft() {
        Order order = Order.builder()
                .type(OrderType.builder().name(OrderTypeEnum.DELIVERY).build()).build();
        Address userAddress = Address.builder()
                .id(1L)
                .city("Санкт-Петербург")
                .street("Гагарина")
                .house("4")
                .room((short) 4)
                .build();

        User user = User.builder()
                .name("Иван")
                .addresses(List.of(userAddress))
                .phone("+7888 888 88 88").build();
        OrderCreateRequestDto request = OrderCreateRequestDto.builder().type(OrderTypeEnum.PICKUP).build();
        Order response = orderChain.doFilter(user, order, request);
        assertEquals(OrderStatusEnum.DRAFT,response.getStatus().getStatus());
    }


    private static Order getOrder(ProductList... productLists) {
        return Order.builder()
                .productList(List.of(productLists)
                ).build();
    }

    private static ProductList getProductList(Product withRecipe, int count) {
        return ProductList.builder()
                .product(withRecipe)
                .count(count).build();
    }

    private static Product getWithoutRecipe() {
        return Product.builder()
                .id(2L)
                .name("Миг")
                .price(BigDecimal.valueOf(499.00))
                .isPrescriptionRequired(false)
                .build();
    }

    private static Product getProductWithRecipe() {
        return Product.builder()
                .id(1L)
                .name("Семитикон")
                .price(BigDecimal.valueOf(1000.00))
                .isPrescriptionRequired(true)
                .build();
    }

    private static OrderChain getOrderChain(OrderFilter orderFilter){
        Queue<OrderFilter> orderFilterQueue = new ArrayDeque<>();
        orderFilterQueue.add(orderFilter);
        return new OrderChain(orderFilterQueue);
    }
    private static CompletableFuture<DeliveryResponse> getCompletableFuture() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
                return new MockDeliveryResponse(UUID.randomUUID(), DeliveryStatus.ON_THE_WAY, "Иван", "+7 999 999 99 99");
            } catch (InterruptedException e) {
                return MockDeliveryResponse.error();
            }
        });
    }
}