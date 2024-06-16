package com.aston.aston_project.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Attribute_Value",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "value_id", referencedColumnName = "id")
    )
    @MapKeyJoinColumn (name = "attribute_id",referencedColumnName = "id")
    private Map<Attributes,Values> attributesValues = new HashMap<>();


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "Attribute_Value",
//            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "id")
//    )
//    private List<Attributes> attributes = new ArrayList<>();
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "Attribute_Value",
//            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "value_id", referencedColumnName = "id")
//    )
//    private List<Values> values = new ArrayList<>();
}
