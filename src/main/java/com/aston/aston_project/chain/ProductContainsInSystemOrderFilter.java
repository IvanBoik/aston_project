package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductContainsInSystemOrderFilter implements OrderFilter {
    private ProductRepository productRepository;
    private PharmacyRepository pharmacyRepository;

    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        Map<Long, Integer> productToCount = request.getProducts().stream()
                .collect(Collectors.toMap(ProductRequestDto::getId, ProductRequestDto::getCount));
        List<Product> products = productRepository.findAllById(productToCount.keySet());
        Set<Long> productsIds = products.stream().map(Product::getId).collect(Collectors.toSet());
        boolean allProductsExists = productsIds.containsAll(productToCount.keySet());
        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new NotFoundDataException("Pharmacy not found"));
        List<Product> productsInPharmacy = pharmacy.getProduct().stream().map(PharmacyProduct::getProduct).toList();
        Set<Long> productsInPharmacyIds = productsInPharmacy.stream().map(Product::getId).collect(Collectors.toSet());
        boolean pharmacyContainsAllProducts = productsInPharmacyIds.containsAll(productToCount.keySet());
        if (allProductsExists) {
            if (pharmacyContainsAllProducts) {
                order.setProductList(products.stream().map(p -> ProductList
                                .builder()
                                .product(p)
                                .order(order)
                                .count(productToCount.get(p.getId()))
                                .build()
                        ).toList()
                );
            } else {
                throw new NotFoundDataException("Product not contains in pharmacy");
            }
        } else {
            throw new NotFoundDataException("Product with id not found");
        }
    }
}
