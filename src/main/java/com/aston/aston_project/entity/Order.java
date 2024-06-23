package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User idUser;

    @CreationTimestamp
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address idAddress;

    @ManyToOne
    private OrderType type;

    @ManyToOne
    private OrderStatus status;

    @ManyToOne
    private OrderPaymentType paymentType;
}
