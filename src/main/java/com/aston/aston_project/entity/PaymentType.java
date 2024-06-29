package com.aston.aston_project.entity;

import com.aston.aston_project.entity.en.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private PaymentTypeEnum type;
}
