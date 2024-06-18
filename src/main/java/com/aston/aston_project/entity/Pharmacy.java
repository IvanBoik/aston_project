package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NamedEntityGraph(name = "withAddress",attributeNodes = {@NamedAttributeNode("address")})
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PharmacyProduct> product = new ArrayList<>();
}
