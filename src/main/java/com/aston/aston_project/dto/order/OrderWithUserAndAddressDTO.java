package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.AddressResponseDTO;
import com.aston.aston_project.dto.UserResponseDTO;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderStatusEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderWithUserAndAddressDTO extends OrderWithAddressDTO{
    private UserResponseDTO user;
}
