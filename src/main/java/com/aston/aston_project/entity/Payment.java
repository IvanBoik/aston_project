package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDate date;
    @UpdateTimestamp
    private LocalTime time;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private PaymentType paymentType;

    @ManyToOne
    private Order order;
    @ManyToOne
    private User from;
    @ManyToOne
    private Pharmacy to;


}
