package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "type_payment")
@Getter
@Setter
public class TypePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
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
