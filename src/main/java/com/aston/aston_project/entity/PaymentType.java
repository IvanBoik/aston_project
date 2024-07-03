package com.aston.aston_project.entity;

import com.aston.aston_project.entity.en.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private PaymentTypeEnum type;

    @Builder
    public PaymentType(Long id, PaymentTypeEnum type) {
        this.id = id;
        this.type = type;
    }
}
