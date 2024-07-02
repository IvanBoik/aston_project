package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.util.exception.IncorrectDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecipesOrderFilter implements OrderFilter{
    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        Set<Long> requestRecipeIds = request.getProducts().stream().filter(productRequestDto -> productRequestDto.getRecipe() != null).map(ProductRequestDto::getId).collect(Collectors.toSet());
        if(request.getType() == OrderTypeEnum.PICKUP && !requestRecipeIds.isEmpty()){
            List<Long> productWithRecipeIds = order.getProductList().stream().map(ProductList::getProduct).filter(Product::getIsPrescriptionRequired).map(Product::getId).toList();
            boolean containsAll = requestRecipeIds.containsAll(productWithRecipeIds);
            if (containsAll) {
                List<Recipe> recipeList = request.getProducts().stream().map(ProductRequestDto::getRecipe).map(r -> Recipe.builder()
                        .link(r)
                        .build()
                ).toList();
                order.setRecipeList(recipeList);
            }
        }else{
            throw new IncorrectDataException("recipe orders cannot be delivered");
        }
    }
}
