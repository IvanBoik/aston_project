package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    private ProductType type;

    @Column(name = "is_recipe")
    private Boolean isPrescriptionRequired;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Producer producer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attribute_value",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "value_id", referencedColumnName = "id")
    )
    @MapKeyJoinColumn(name = "attribute_id", referencedColumnName = "id")
    private Map<Attribute, Value> attributesValues = new HashMap<>();

    public void setAttributesValues(Attribute att, Value val) {
        this.attributesValues.put(att, val);
    }
}
