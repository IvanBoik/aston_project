package com.aston.aston_project.service;

import com.aston.aston_project.api.recipe.util.RecipeChecker;
import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.dto.order.OrderWithUserAndAddressDTO;
import com.aston.aston_project.dto.order.OrderWithProductAndRecipeDTO;
import com.aston.aston_project.dto.order.OrderWithProductsDTO;
import com.aston.aston_project.dto.order.SuspiciousOrderDTO;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.OrderStatus;
import com.aston.aston_project.entity.Recipe;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.mapper.OrderMapper;
import com.aston.aston_project.repository.OrderRepository;
import com.aston.aston_project.repository.OrderStatusRepository;
import com.aston.aston_project.repository.ProductListRepository;
import com.aston.aston_project.repository.RecipeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * A service describing the functionality of the manager
 * @author K.Zemlyakov
 */
@Service
@AllArgsConstructor
public class ManagerService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderStatusRepository orderStatusRepository;
    private RecipeChecker recipeChecker;
    private RecipeRepository recipeRepository;
    private ProductListRepository productListRepository;


    /**
     * Method searches all orders
     * @return Extended {@link Order} information
     * @author K. Zemlyakov
     */
    public List<OrderWithUserAndAddressDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toExtendedWithUserDTO).toList();
    }

    /**
     * Method searches suspicious orders
     * @return list {@link SuspiciousOrderDTO} which contains orders with suspicious relation between count and price
     * @author K. Zemlyakov
     */
    public List<SuspiciousOrderDTO> getAllSuspiciousOrders(){
        return orderRepository.getAllSuspiciousOrders();
    }


    /**
     * Method setting status to order
     * @param order {@link Order} order where status have to be set
     * @param status {@link OrderStatusEnum} status which have to be set
     * @return Extended {@link Order} information
     * @author K. Zemlyakov
     */
    @Transactional
    public OrderWithUserAndAddressDTO setOrderStatus(Long order, OrderStatusEnum status) {
        Optional<Order> optionalOrder = orderRepository.findById(order);
        Order foundOrder = optionalOrder.orElseThrow(()->new NotFoundDataException("Order with this id not found"));
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findByStatus(status);
        optionalOrderStatus.ifPresentOrElse(foundOrder::setStatus,() -> foundOrder.setStatus(OrderStatus.builder().status(status).build()));
        return orderMapper.toExtendedWithUserDTO(orderRepository.save(foundOrder));
    }

    /**
     * Method searches orders which contain products with prescription
     * @return list of order dto which contains order info and recipe info
     * @author K. Zemlyakov
     */

    public List<OrderWithProductAndRecipeDTO> getRecipeOrders(){
        List<OrderWithProductsDTO> allRecipeOrders = productListRepository.getAllRecipeOrders();
        return allRecipeOrders.stream().map(orderMapper::toOrderWithProductAndRecipeDTO).toList();
    }

    /**
     * Method searches recipe from repository and the via {@link RecipeChecker} api validate its link
     * @param id recipe id
     * @return dto of recipe
     * @throws NotFoundDataException when recipe was not found
     * @author K. Zemlyakov
     */
    public RecipeCheckerResponse checkRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        Recipe recipe = recipeOptional.orElseThrow(() -> new NotFoundDataException("Recipe not found"));
        return recipeChecker.check(recipe);
    }
}
