package com.aston.aston_project.dto;

import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class OrderExtendedResponseDTO {
    private Long id;
    private UserResponseDTO user;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    private LocalDateTime time;
    private AddressResponseDTO address;
    private OrderTypeEnum type;
    private OrderStatusEnum status;
    private OrderPaymentEnum paymentType;
}
