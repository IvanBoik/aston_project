package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@NamedEntityGraph(name = "withPharmacy", attributeNodes = {@NamedAttributeNode("pharmacy")})
@NamedEntityGraph(name = "withProduct", attributeNodes = {@NamedAttributeNode("product")})
@NamedEntityGraph(name = "withPharmacyAndProduct", attributeNodes = {
        @NamedAttributeNode("pharmacy"), @NamedAttributeNode("product")})
public class PharmacyProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer count;
}
