package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.util.exception.IncorrectDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * filter which setting product count in pharmacy
 * @author K. Zemlyakov
 */
@Component
@AllArgsConstructor
public class ProductSalesOrderFilter implements OrderFilter {
    private PharmacyRepository pharmacyRepository;

    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        Map<Long, Integer> idsToCount = order.getProductList().stream().collect(Collectors.toMap(pl -> pl.getProduct().getId(), ProductList::getCount));
        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId()).get();
        pharmacy.getProduct().stream()
                .filter(pharmacyProduct -> idsToCount.containsKey(pharmacyProduct.getProduct().getId()))
                .forEach(pharmacyProduct -> {
                    Integer countToSales = idsToCount.get(pharmacyProduct.getProduct().getId());
                    Integer countWhichCanBeSold = pharmacyProduct.getCount();
                    int remaining = countWhichCanBeSold - countToSales;
                    if (remaining < 0)
                        throw new IncorrectDataException("Product with this count cannot be sold");
                    pharmacyProduct.setCount(remaining);
                });
    }
}
