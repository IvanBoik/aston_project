package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "public",name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @CreationTimestamp
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private OrderType type;

    @ManyToOne(cascade = CascadeType.ALL)
    private OrderStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private OrderPaymentType paymentType;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<ProductList> productList = new ArrayList<>();

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Recipe> recipeList = new ArrayList<>();

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Payment> payments = new ArrayList<>();
}
