package com.aston.aston_project.mapper;

import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper
public interface DefaultMapper {
    default LocalDateTime map(Timestamp value){
        return  value == null? null: LocalDateTime.from(value.toLocalDateTime());
    }

    default OrderTypeEnum map(OrderType value){
        return value == null? null:value.getName();
    }

    default OrderStatusEnum map(OrderStatus value){
        return value == null? null:value.getStatus();
    }

    default OrderPaymentEnum map(OrderPaymentType value){
        return value == null? null: value.getName();
    }

    default Product mapProduct(ProductList value){
        return value.getProduct();
    }

    default Order mapOrder(ProductList value){
        return value.getOrder();
    }
}
