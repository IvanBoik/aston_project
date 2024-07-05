package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.order.*;
import com.aston.aston_project.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AddressMapper.class, UserMapper.class, DefaultMapper.class, RecipeMapper.class, ProductMapper.class, ProductListMapper.class})
public interface OrderMapper {


    OrderWithUserAndAddressDTO toExtendedWithUserDTO(Order order);
    OrderDTO toExtendedWithoutUserDTO(Order order);

    @Mapping(target = "id", source = "value.order.id")
    @Mapping(target = "user", source = "value.order.user")
    @Mapping(target = "time", source = "value.order.time")
    @Mapping(target = "product", source = "value.product")
    @Mapping(target = "recipes", source = "value.order.recipeList")
    @Mapping(target = "product.count", source = "value.count")
    OrderWithProductAndRecipeDTO toOrderWithProductAndRecipeDTO(OrderWithProductsDTO value);

    @Mapping(target = "products",source = "order.productList")
    OrderWithAddressAndProductsDTO toOrderWithAddressAndProductsDto(Order order);
    @Mapping(target = "products",source = "order.productList")
    OrderWithAddressProductsAndRecipesDTO toOrderWithAddressProductsAndRecipesDTO(Order order);

}
