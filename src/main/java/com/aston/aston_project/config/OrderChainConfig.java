package com.aston.aston_project.config;

import com.aston.aston_project.chain.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Order chain configuration
 * @author K. Zemlyakov
 */
@Configuration
@AllArgsConstructor
public class OrderChainConfig {
    private ProductContainsInSystemOrderFilter productContainsInSystemFilter;
    private RecipesOrderFilter recipesOrderFilter;
    private PaymentOrderFilter paymentOrderFilter;
    private DeliveryOrderFilter deliveryOrderFilter;
    private AddressOrderFilter addressOrderFilter;
    private ProductSalesOrderFilter productSalesOrderFilter;
    @Bean
    @Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Queue<OrderFilter> orderFilters(){
        Queue<OrderFilter> orderFilters = new ArrayDeque<>();
        orderFilters.add(productContainsInSystemFilter);
        orderFilters.add(recipesOrderFilter);
        orderFilters.add(addressOrderFilter);
        orderFilters.add(paymentOrderFilter);
        orderFilters.add(productSalesOrderFilter);
        orderFilters.add(deliveryOrderFilter);
        return orderFilters;
    }
}
