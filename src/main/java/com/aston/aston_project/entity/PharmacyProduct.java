package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PHARMACY_PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
