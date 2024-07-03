package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "PHARMACY_PRODUCT")
public class PharmacyProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private Integer count;
}
