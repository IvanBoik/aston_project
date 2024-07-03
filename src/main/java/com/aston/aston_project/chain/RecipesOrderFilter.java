package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.util.exception.IncorrectDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecipesOrderFilter implements OrderFilter {
    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        List<ProductList> productList = order.getProductList();
        Set<Long> productRecipes = productList.stream().map(ProductList::getProduct).filter(Product::getIsPrescriptionRequired).map(Product::getId).collect(Collectors.toSet());
        if (request.getType() == OrderTypeEnum.PICKUP) {
            Set<Long> productWithRecipeIds = request.getProducts().stream().filter(pr -> pr.getRecipe() != null).map(ProductRequestDto::getId).collect(Collectors.toSet());
            boolean containsAll = productWithRecipeIds.containsAll(productRecipes);
            if (containsAll && !productRecipes.isEmpty()) {
                List<Recipe> recipeList = request.getProducts().stream().filter(p -> productRecipes.contains(p.getId())).map(r -> Recipe.builder()
                        .link(r.getRecipe())
                        .order(order)
                        .productList(productList.stream().filter(pr-> Objects.equals(pr.getProduct().getId(), r.getId())).findAny().get())
                        .build()
                ).toList();
                order.setRecipeList(recipeList);
            } else {
                throw new IncorrectDataException("Product recipes not present");
            }
        } else {
            throw new IncorrectDataException("Recipe orders cannot be delivered");
        }
    }
}
