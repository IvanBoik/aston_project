package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.util.exception.IncorrectDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSalesOrderFilterTest {
    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private OrderChain orderChain;

    @Mock
    private Pharmacy pharmacy;

    @InjectMocks
    private ProductSalesOrderFilter orderFilter;
    @Mock
    private Order orderConfiguredBefore;

    @BeforeEach
    public void init() {
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

        ProductList withRecipeProductList = ProductList.builder()
                .product(withRecipe)
                .count(100).build();

        ProductList withoutRecipeProductList = ProductList.builder()
                .product(withoutRecipe)
                .count(1).build();

        orderConfiguredBefore = Order.builder()
                .productList(List.of(
                        withRecipeProductList,
                        withoutRecipeProductList)
                ).build();

        PharmacyProduct pharmacyProductWithRecipe = PharmacyProduct.builder()
                .product(withRecipe)
                .count(101).build();

        PharmacyProduct pharmacyProductWithoutRecipe = PharmacyProduct.builder()
                .product(withoutRecipe)
                .count(10).build();
        pharmacy = Pharmacy.builder()
                .product(List.of(
                        pharmacyProductWithRecipe,
                        pharmacyProductWithoutRecipe
                )).balance(BigDecimal.valueOf(100.00)).build();

        when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
    }

    @Test
    public void process_productCountInPharmacyCorrectlyWorking(){
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .pharmacyId(1L).build();
        Order order = orderChain.doFilter(User.builder().build(), orderConfiguredBefore, request, orderChain);
        assertEquals(1,pharmacy.getProduct().get(0).getCount());
        assertEquals(9,pharmacy.getProduct().get(1).getCount());
    }

    @Test
    public void process_productCountGreaterThanInSystem_throwsIncorrectDataException(){
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
        ProductList withRecipeProductList = ProductList.builder()
                .product(withRecipe)
                .count(1000).build();

        ProductList withoutRecipeProductList = ProductList.builder()
                .product(withoutRecipe)
                .count(1).build();

        orderConfiguredBefore = Order.builder()
                .productList(List.of(
                        withRecipeProductList,
                        withoutRecipeProductList)
                ).build();

        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .pharmacyId(1L).build();

        assertThrows(IncorrectDataException.class,()->orderChain.doFilter(User.builder().build(), orderConfiguredBefore, request, orderChain));
    }

}