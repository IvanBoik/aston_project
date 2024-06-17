package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Type type;

    @Column(name = "recepie")
    private Boolean isPrescriptionRequired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Producer producer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attribute_value",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "value_id", referencedColumnName = "id")
    )
    @MapKeyJoinColumn (name = "attribute_id",referencedColumnName = "id")
    private Map<Attribute, Value> attributesValues = new HashMap<>();

}
