package com.aston.aston_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "public",name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "WISH_LIST",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> wishList = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ADDRESS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses = new ArrayList<>();

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private Double discounts;
    private BigDecimal balance;
}
