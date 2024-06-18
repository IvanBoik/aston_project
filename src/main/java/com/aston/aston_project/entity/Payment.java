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
    @Enumerated(EnumType.STRING)
    private StatusTransaction statusId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromId;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private Pharmacy toId;


}
