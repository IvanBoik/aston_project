package com.aston.aston_project.entity;

import com.aston.aston_project.entity.en.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_status_type")
@Getter
@Setter
@NoArgsConstructor
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private OrderStatusEnum status;

    @Builder
    public OrderStatus(Long id, OrderStatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
