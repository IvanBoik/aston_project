package com.aston.aston_project.dto.order;

import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    private LocalDateTime time;
    private OrderTypeEnum type;
    private OrderStatusEnum status;
    private OrderPaymentEnum paymentType;
}
