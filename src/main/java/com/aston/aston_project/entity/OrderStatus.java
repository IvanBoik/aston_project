package com.aston.aston_project.entity;

import com.aston.aston_project.entity.en.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "order_status_type")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    @NonNull
    private OrderStatusEnum status;
}
