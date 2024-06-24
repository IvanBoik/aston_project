package com.aston.aston_project.service;

import com.aston.aston_project.api.recipe.util.RecipeChecker;
import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.dto.OrderExtendedResponseDTO;
import com.aston.aston_project.dto.OrderWithRecipeDTO;
import com.aston.aston_project.dto.SuspiciousOrderDTO;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.OrderStatus;
import com.aston.aston_project.entity.Recipe;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.mapper.OrderMapper;
import com.aston.aston_project.repository.OrderRepository;
import com.aston.aston_project.repository.OrderStatusRepository;
import com.aston.aston_project.repository.RecipeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderStatusRepository orderStatusRepository;
    private RecipeChecker recipeChecker;
    private RecipeRepository recipeRepository;

    public List<OrderExtendedResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toExtendedDTO).toList();
    }

    public List<SuspiciousOrderDTO> getAllSuspiciousOrders(){
        return orderRepository.getAllSuspiciousOrders();
    }

    @Transactional
    public OrderExtendedResponseDTO setOrderStatus(Long order, OrderStatusEnum status) {
        Optional<Order> optionalOrder = orderRepository.findById(order);
        optionalOrder.orElseThrow(()->new NotFoundDataException("Order with this id not found"));
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findByStatus(status);
        Order foundOrder = optionalOrder.get();
        optionalOrderStatus.ifPresentOrElse(foundOrder::setStatus,() -> foundOrder.setStatus(new OrderStatus(status)));
        return orderMapper.toExtendedDTO(orderRepository.save(foundOrder));
    }

    public List<OrderWithRecipeDTO> getRecipeOrders(){
        List<Order> allRecipeOrders = orderRepository.getAllRecipeOrders();
        return allRecipeOrders.stream().map(orderMapper::toOrderWithRecipeDTO).toList();
    }

    public RecipeCheckerResponse checkRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        recipeOptional.orElseThrow(()->new NotFoundDataException("Recipe not found"));
        Recipe recipe = recipeOptional.get();
        return recipeChecker.check(recipe);
    }
}
