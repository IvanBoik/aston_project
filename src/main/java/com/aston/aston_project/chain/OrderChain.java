package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Queue;

/**
 * Order creation chain
 * note that queue scope is request because every request queue must be reinitialized
 * @author K. Zemlyakov
 */
@Component
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
