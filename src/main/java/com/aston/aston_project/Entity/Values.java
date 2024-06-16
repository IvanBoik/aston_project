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
public class Values {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value;
//    @ManyToMany(mappedBy = "attributesValues")
//    private List<Product> values = new ArrayList<>();
//    @ManyToMany
//    private List<Attributes> attributes = new ArrayList<>();
}
