package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.OrderExtendedResponseDTO;
import com.aston.aston_project.dto.OrderWithRecipeDTO;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.Recipe;
import org.mapstruct.*;
import org.mapstruct.control.MappingControl;

@Mapper(uses = {AddressMapper.class, UserMapper.class, DefaultMapper.class, RecipeMapper.class, ProductMapper.class})
public interface OrderMapper {


    OrderExtendedResponseDTO toExtendedDTO(Order order);


    @Mapping(target = "recipes",source = "order.recipeList")
    @Mapping(target = "products", source = "order.products")
    OrderWithRecipeDTO toOrderWithRecipeDTO(Order order);
}
