package com.aston.aston_project.entity;

import com.aston.aston_project.entity.en.OrderPaymentEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderPaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderPaymentEnum name;
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
