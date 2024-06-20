package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "status")
@Getter
@Setter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
//    ACCEPTED,
//    ON_HOLD,
//    REJECTED,
//    DELIVERED,
//    PARTIALLY_DELIVERED,
//    COMPLETED,
//    PROCESSING,
//    SHIPPED,
//    PARTIALLY_SHIPPED,
//    AWAITING_PAYMENT,
//    PAID,
//    PARTIALLY_PAID,
//    NOT_PAID,
//    AWAITING_SHIPMENT,
//    CHECKED,
//    RETURNED
}
