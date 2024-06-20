package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleValue name;
}
