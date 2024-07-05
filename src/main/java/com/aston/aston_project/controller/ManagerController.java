package com.aston.aston_project.controller;

import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.dto.order.OrderWithProductAndRecipeDTO;
import com.aston.aston_project.dto.order.OrderWithUserAndAddressDTO;
import com.aston.aston_project.dto.order.SuspiciousOrderDTO;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.service.ManagerService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aston.aston_project.AstonProjectApplication.log;

/**
 * Controller which describes all managers' logic
 *
 * @author K. Zemlyakov
 */
@RestController
@RequestMapping(value = "api/v1/managers", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ManagerController {

    private ManagerService managerService;

    @GetMapping("/orders")
    public List<OrderWithUserAndAddressDTO> getAllOrders() {
        log.info("Get request /api/v1/managers/orders");
        return managerService.getAllOrders();
    }

    @GetMapping("/orders/suspicious")
    public List<SuspiciousOrderDTO> getAllSuspiciousOrders() {
        log.info("Get request /api/v1/managers/orders/suspicious");
        return managerService.getAllSuspiciousOrders();
    }

    @PatchMapping("/orders/{order}")
    public OrderWithUserAndAddressDTO switchStatus(@PathVariable @Positive Long order,
                                                   @RequestParam OrderStatusEnum status) {
        log.info("Patch request /api/v1/managers/orders/{}, {}", order, status.toString());
        return managerService.setOrderStatus(order, status);
    }

    @GetMapping("/orders/recipes")
    public List<OrderWithProductAndRecipeDTO> getAllOrdersWithRecipe() {
        log.info("Patch request /api/v1/managers/recipes");
        return managerService.getRecipeOrders();
    }

    @GetMapping("/recipes/{id}/check")
    public RecipeCheckerResponse checkRecipe(@PathVariable @Positive Long id) {
        log.info("Get request /api/v1/managers/orders/{}/check", id);
        return managerService.checkRecipe(id);
    }
}