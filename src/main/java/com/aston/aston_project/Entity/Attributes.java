package com.aston.aston_project.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Attributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String attribute;
//    @ManyToMany(mappedBy = "attributesValues")
//    private List<Product> products = new ArrayList<>();
//    @ManyToMany
//    private List<Values> values = new ArrayList<>();
}
