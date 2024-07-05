package com.aston.aston_project.entity;

import com.aston.aston_project.entity.en.OrderPaymentEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class OrderPaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderPaymentEnum name;

    @Builder
    public OrderPaymentType(Long id, OrderPaymentEnum name) {
        this.id = id;
        this.name = name;
    }

    //    CASH_TO_COURIER,
//    CARD_TO_COURIER,
//    PAYMENT_UPON_RECEIPT,
//    CASH_ON_DELIVERY,
//    BANK_TRANSACTION,
//    CARD,
//    ON_CREDIT,
//    INSTALMENT,
//    ELECTRONIC_WALLET,
//    CRYPTOCURRENCY,
//    CASHLESS,
//    BARTER
}
