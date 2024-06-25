package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.OrderExtendedResponseDTO;
import com.aston.aston_project.dto.OrderWithRecipeDTO;
import com.aston.aston_project.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AddressMapper.class, UserMapper.class, DefaultMapper.class, RecipeMapper.class, ProductMapper.class})
public interface OrderMapper {


    OrderExtendedResponseDTO toExtendedDTO(Order order);


    @Mapping(target = "recipes",source = "order.recipeList")
    @Mapping(target = "products", source = "order.productList")
    OrderWithRecipeDTO toOrderWithRecipeDTO(Order order);
}
