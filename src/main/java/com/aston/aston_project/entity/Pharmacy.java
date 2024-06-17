package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PHARMACY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    private List<PharmacyProduct> product = new ArrayList<>();
}
