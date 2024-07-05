package com.aston.aston_project.service;

import com.aston.aston_project.api.recipe.client.MockRecipeCheckerResponse;
import com.aston.aston_project.api.recipe.util.RecipeChecker;
import com.aston.aston_project.dto.order.OrderWithUserAndAddressDTO;
import com.aston.aston_project.dto.order.OrderWithProductAndRecipeDTO;
import com.aston.aston_project.dto.order.OrderWithProductsDTO;
import com.aston.aston_project.dto.order.SuspiciousOrderDTO;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.OrderStatus;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.Recipe;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.mapper.OrderMapper;
import com.aston.aston_project.repository.OrderRepository;
import com.aston.aston_project.repository.OrderStatusRepository;
import com.aston.aston_project.repository.ProductListRepository;
import com.aston.aston_project.repository.RecipeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @Mock
    private RecipeChecker recipeChecker;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ProductListRepository productListRepository;

    @InjectMocks
    private ManagerService service;

    @Test
    void getAllOrders_returnsNotEmptyList() {
        Order order = Order.builder().build();
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.toExtendedWithUserDTO(order)).thenReturn(new OrderWithUserAndAddressDTO());
        assertEquals(1,service.getAllOrders().size());
        verify(orderRepository).findAll();

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void getAllOrders_returnsEmptyList(){
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());

        assertEquals(0,service.getAllOrders().size());

        verify(orderRepository).findAll();

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void getAllSuspiciousOrders_returnsNotEmptyList(){
        when(orderRepository.getAllSuspiciousOrders()).thenReturn(List.of(new SuspiciousOrderDTO() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public Product getProduct() {
                return Product.builder().build();
            }

            @Override
            public Long getCount() {
                return 1L;
            }
        }));

        assertEquals(1,service.getAllSuspiciousOrders().size());
        verify(orderRepository).getAllSuspiciousOrders();

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void getAllSuspiciousOrders_returnsEmptyList(){
        when(orderRepository.getAllSuspiciousOrders()).thenReturn(new ArrayList<>());

        assertEquals(0,service.getAllSuspiciousOrders().size());

        verify(orderRepository).getAllSuspiciousOrders();

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void setOrderStatus_returnsValidaData(){
        Order order = Order.builder().id(1L).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderStatusRepository.findByStatus(OrderStatusEnum.DECLINED)).thenReturn(Optional.of(OrderStatus.builder().status(OrderStatusEnum.DECLINED).build()));
        when(orderRepository.save(order)).thenReturn(order);
        OrderWithUserAndAddressDTO response = new OrderWithUserAndAddressDTO();
        response.setId(1L);
        response.setStatus(OrderStatusEnum.DECLINED);
        when(orderMapper.toExtendedWithUserDTO(order)).thenReturn(response);

        assertEquals(OrderStatusEnum.DECLINED,service.setOrderStatus(1L,OrderStatusEnum.DECLINED).getStatus());

        verify(orderRepository).findById(1L);
        verify(orderStatusRepository).findByStatus(OrderStatusEnum.DECLINED);
        verify(orderMapper).toExtendedWithUserDTO(order);

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void setOrderStatus_throwsNotFoundDataException(){
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundDataException.class,()->service.setOrderStatus(1L,OrderStatusEnum.DECLINED));

        verify(orderRepository).findById(1L);

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void getRecipeOrders_returnsNotEmptyList(){
        OrderWithProductsDTO order = new OrderWithProductsDTO() {
            @Override
            public Order getOrder() {
                return Order.builder().build();
            }

            @Override
            public Product getProduct() {
                return Product.builder().build();
            }

            @Override
            public Integer getCount() {
                return 1;
            }
        };
        when(productListRepository.getAllRecipeOrders()).thenReturn(List.of(order));
        when(orderMapper.toOrderWithProductAndRecipeDTO(order)).thenReturn(new OrderWithProductAndRecipeDTO());

        assertEquals(1,service.getRecipeOrders().size());
        verify(productListRepository).getAllRecipeOrders();
        verify(orderMapper).toOrderWithProductAndRecipeDTO(order);

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void getRecipeOrders_returnsEmptyList(){

        when(productListRepository.getAllRecipeOrders()).thenReturn(new ArrayList<>());

        assertEquals(0,service.getRecipeOrders().size());
        verify(productListRepository).getAllRecipeOrders();

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void checkRecipe_returnsValidaData(){
        Recipe recipe = Recipe.builder().build();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeChecker.check(recipe)).thenReturn(new MockRecipeCheckerResponse("product", LocalDateTime.now(),true));

        assertEquals(true,service.checkRecipe(1L).isValid());

        verify(recipeRepository).findById(1L);
        verify(recipeChecker).check(recipe);

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }

    @Test
    void checkRecipe_throwsNotFoundDataException(){
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundDataException.class,()->service.checkRecipe(1L));

        verify(recipeRepository).findById(1L);

        verifyNoMoreInteractions(orderMapper,orderRepository,orderStatusRepository,recipeRepository,recipeChecker,productListRepository);
    }




}
