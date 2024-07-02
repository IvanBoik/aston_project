package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.product.ProductRequestDto;
import com.aston.aston_project.entity.OrderPaymentType;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderCreateRequestDto {
    @NotNull
    @Valid
    private List<ProductRequestDto> products;
    private OrderTypeEnum type;
    @Positive
    private Long addressId;
    @NotNull
    @Positive
    private Long pharmacyId;
    @NotNull
    private OrderPaymentEnum paymentType;
    @AssertFalse(message = "If order type is 'PICKUP' addressId cannot be set")
    private boolean isPickupAndAddressSet(){
        return addressId !=null && type == OrderTypeEnum.PICKUP;
    }

    @Builder
    public OrderCreateRequestDto(List<ProductRequestDto> products, OrderTypeEnum type, Long addressId, Long pharmacyId, OrderPaymentEnum paymentType) {
        this.products = products;
        this.type = type;
        this.addressId = addressId;
        this.pharmacyId = pharmacyId;
        this.paymentType = paymentType;
    }
}
