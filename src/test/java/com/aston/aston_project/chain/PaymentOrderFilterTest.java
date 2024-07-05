package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.PaymentTypeEnum;
import com.aston.aston_project.repository.OrderPaymentTypeRepository;
import com.aston.aston_project.repository.PaymentTypeRepository;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.util.exception.BalanceProcessingException;
import lombok.NonNull;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentOrderFilterTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PharmacyRepository pharmacyRepository;
    @Mock
    private OrderPaymentTypeRepository orderPaymentTypeRepository;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;
    @Mock
    private Order orderConfiguredBefore;
    @Mock
    private OrderChain orderChain;
    @Mock
    private Pharmacy pharmacy;
    @InjectMocks
    private PaymentOrderFilter orderFilter;

    @BeforeEach
    public void init() {
        Queue<OrderFilter> orderFilterQueue = new ArrayDeque<>();
        orderFilterQueue.add(orderFilter);
        orderChain = new OrderChain(orderFilterQueue);
        Product withRecipe = getRecipeOrder();
        Product withoutRecipe = getWithoutRecipeOrder();
        PharmacyProduct pharmacyProduct = getPharmacyProduct(withRecipe, 100);
        PharmacyProduct pharmacyProduct2 = getPharmacyProduct(withoutRecipe, 99);
        pharmacy = getPharmacy(pharmacyProduct, pharmacyProduct2);

        List<ProductList> list = getProductLists(withRecipe, withoutRecipe);
        Recipe recipe = getRecipe(list);
        when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
        orderConfiguredBefore = Order.builder()
                .productList(list)
                .recipeList(List.of(recipe)).build();
    }

    @Test
    public void process_paymentSuccess(){
        when(orderPaymentTypeRepository.findByName(OrderPaymentEnum.CARD)).thenReturn(Optional.ofNullable(OrderPaymentType.builder().id(1L).name(OrderPaymentEnum.CARD).build()));
        when(paymentTypeRepository.findByType(PaymentTypeEnum.PAYED)).thenReturn(Optional.of(PaymentType.builder().id(1L).type(PaymentTypeEnum.PAYED).build()));
        User user = User.builder()
                .balance(BigDecimal.valueOf(1499.00)).build();
        ProductRequestDto requestProduct = ProductRequestDto.builder()
                .id(1L)
                .count(1)
                .recipe("01Д1234567890")
                .build();
        OrderCreateRequestDto request =  OrderCreateRequestDto.builder()
                .paymentType(OrderPaymentEnum.CARD)
                .products(List.of(requestProduct))
                .pharmacyId(1L)
                .build();
        Order order = orderChain.doFilter(user, orderConfiguredBefore, request);
        assertEquals(0, BigDecimal.ZERO.compareTo(user.getBalance()));
        assertEquals(0, BigDecimal.valueOf(1599.00).compareTo(pharmacy.getBalance()));
    }

    @Test
    public void process_balancesNotSet_whenTypeIsCash(){
        User user = User.builder()
                .balance(BigDecimal.valueOf(1000.00)).build();
        ProductRequestDto requestProduct = ProductRequestDto.builder()
                .id(1L)
                .count(1)
                .recipe("01Д1234567890")
                .build();
        OrderCreateRequestDto request =  OrderCreateRequestDto.builder()
                .paymentType(OrderPaymentEnum.CASH)
                .products(List.of(requestProduct))
                .pharmacyId(1L)
                .build();
        Order order = orderChain.doFilter(user, orderConfiguredBefore, request);
        assertEquals(0, BigDecimal.valueOf(1000.00).compareTo(user.getBalance()));
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(pharmacy.getBalance()));
    }

    @Test
    public void process_paymentDeclined_whenUserBalanceIsLower_throwsBalanceProcessingException(){
        User user = User.builder()
                .balance(BigDecimal.valueOf(999.00)).build();
        ProductRequestDto requestProduct = ProductRequestDto.builder()
                .id(1L)
                .count(1)
                .recipe("01Д1234567890")
                .build();
        OrderCreateRequestDto request =  OrderCreateRequestDto.builder()
                .paymentType(OrderPaymentEnum.CARD)
                .products(List.of(requestProduct))
                .pharmacyId(1L)
                .build();

        assertThrows(BalanceProcessingException.class,()->orderChain.doFilter(user, orderConfiguredBefore, request));
        assertEquals(0, BigDecimal.valueOf(999.00).compareTo(user.getBalance()));
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(pharmacy.getBalance()));
    }

    private static Recipe getRecipe(List<ProductList> list) {
        return Recipe.builder()
                .productList(list.get(0))
                .link("01Д1234567890")
                .build();
    }

    @NonNull
    private static List<ProductList> getProductLists(Product... products) {
        return Stream.of(products)
                .map(p -> ProductList.builder()
                        .id(p.getId())
                        .product(p)
                        .count(1)
                        .build()).toList();
    }

    private static Pharmacy getPharmacy(PharmacyProduct... pharmacyProducts) {
        return Pharmacy.builder()
                .id(1L)
                .product(List.of(pharmacyProducts))
                .balance(BigDecimal.valueOf(100.00))
                .build();
    }

    private static PharmacyProduct getPharmacyProduct(Product withRecipe, int count) {
        return PharmacyProduct.builder()
                .product(withRecipe)
                .count(count).build();
    }

    private static Product getWithoutRecipeOrder() {
        return Product.builder()
                .id(2L)
                .name("Миг")
                .price(BigDecimal.valueOf(499.00))
                .isPrescriptionRequired(false)
                .build();
    }

    private static Product getRecipeOrder() {
        return Product.builder()
                .id(1L)
                .name("Семитикон")
                .price(BigDecimal.valueOf(1000.00))
                .isPrescriptionRequired(true)
                .build();
    }
}