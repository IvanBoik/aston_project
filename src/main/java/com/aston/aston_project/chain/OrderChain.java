package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class OrderChain {

    private Queue<OrderFilter> chain;

    public Order doFilter(User user, OrderCreateRequestDto request){
        return doFilter(user,new Order(),request);
    }

    Order doFilter(User user,Order order, OrderCreateRequestDto requestDto){
        while(!chain.isEmpty()){
            chain.remove().process(user,order,requestDto);
        }
        return order;
    }

}
