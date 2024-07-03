package com.aston.aston_project.controller;

import com.aston.aston_project.api.recipe.util.RecipeCheckerResponse;
import com.aston.aston_project.dto.OrderExtendedResponseDTO;
import com.aston.aston_project.dto.OrderWithProductAndRecipeDTO;
import com.aston.aston_project.dto.SuspiciousOrderDTO;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.service.ManagerService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller which describes all managers' logic
 * @author K. Zemlyakov
 */
@RestController
@RequestMapping(value ="/api/v1/managers",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ManagerController {

    private ManagerService managerService;

    @GetMapping("/orders")
    public List<OrderExtendedResponseDTO> getAllOrders(){
        return managerService.getAllOrders();
    }

    @GetMapping("/orders/suspicious")
    public List<SuspiciousOrderDTO> getAllSuspiciousOrders(){
        return managerService.getAllSuspiciousOrders();
    }

    @PatchMapping("/orders/{order}")
    public OrderExtendedResponseDTO switchStatus(@PathVariable @Positive Long order,
                                                 @RequestParam OrderStatusEnum status) {
        return managerService.setOrderStatus(order, status);
    }

    @GetMapping("/orders/recipes")
    public List<OrderWithProductAndRecipeDTO> getAllOrdersWithRecipe(){
        return managerService.getRecipeOrders();
    }

    @GetMapping("/recipes/{id}/check")
    public RecipeCheckerResponse checkRecipe(@PathVariable @Positive Long id){
        return managerService.checkRecipe(id);
    }
}
