package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddressOrderFilterTest {
    @Mock
    private PharmacyRepository pharmacyRepository;
    @Mock
    private OrderChain orderChain;
    @InjectMocks
    private AddressOrderFilter orderFilter;

    @BeforeEach
    public void init() {
        Queue<OrderFilter> orderFilterQueue = new ArrayDeque<>();
        orderFilterQueue.add(orderFilter);
        orderChain = new OrderChain(orderFilterQueue);
        Product product = Product.builder()
                .id(1L)
                .name("Семитикон")
                .price(BigDecimal.valueOf(1000.00))
                .isPrescriptionRequired(true)
                .build();
        PharmacyProduct productPharmacy = PharmacyProduct.builder()
                .product(product)
                .count(300)
                .build();
        Pharmacy pharmacy = Pharmacy.builder()
                .id(1L)
                .address(Address.builder()
                        .id(2L)
                        .city("Санкт-Петербург")
                        .street("Кутузовский проспект")
                        .build()
                )
                .product(
                        List.of(productPharmacy))
                .build();
        when(pharmacyRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
    }

    @Test
    public void process_settingPharmacyAddress(){
        Address userAddress = Address.builder().id(1L).build();
        User user = User.builder()
                .addresses(List.of(userAddress)).build();
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

        Order order = orderChain.doFilter(user, request);
        assertEquals(2L,order.getAddress().getId());
    }

    @Test
    public void process_settingPharmacyAddress_pharmacyNotFound(){
        Address userAddress = Address.builder().id(1L).build();
        User user = User.builder()
                .addresses(List.of(userAddress)).build();
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.PICKUP)
                .paymentType(OrderPaymentEnum.CASH)
                .pharmacyId(2L)
                .products(List.of(
                        ProductRequestDto.builder()
                                .id(2L)
                                .recipe("01Д1234567890")
                                .count(100)
                                .build()
                ))
                .addressId(2L)
                .build();

        assertThrows(NotFoundDataException.class,()->orderChain.doFilter(user, request));
    }

    @Test
    public void process_settingUserAddress(){
        Address userAddress = Address.builder().id(1L).build();
        User user = User.builder()
                .addresses(List.of(userAddress)).build();
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.DELIVERY)
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

        Order order = orderChain.doFilter(user, request);
        assertEquals(1L,order.getAddress().getId());
    }

    @Test
    public void process_settingUserAddress_addressNotFound(){
        Address userAddress = Address.builder().id(1L).build();
        User user = User.builder()
                .addresses(List.of(userAddress)).build();
        OrderCreateRequestDto request = OrderCreateRequestDto.builder()
                .type(OrderTypeEnum.DELIVERY)
                .paymentType(OrderPaymentEnum.CASH)
                .pharmacyId(2L)
                .products(List.of(
                        ProductRequestDto.builder()
                                .id(2L)
                                .recipe("01Д1234567890")
                                .count(100)
                                .build()
                ))
                .addressId(2L)
                .build();

        assertThrows(NotFoundDataException.class,()->orderChain.doFilter(user, request));
    }

}