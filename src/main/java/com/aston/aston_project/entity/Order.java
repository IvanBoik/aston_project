package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User idUser;

    @Column(name = "time")
    @CreationTimestamp
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private Address idAddress;

    @Enumerated(EnumType.STRING)
    private TypeOrder typeOrder;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private TypePayment typePayment;
}
