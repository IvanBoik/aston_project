package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
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

    @ManyToOne
    private OrderType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private OrderStatus status;

    @ManyToOne
    private OrderPaymentType paymentType;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<ProductList> productList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_list",
            joinColumns = @JoinColumn(
                    name = "order_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"))
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<Recipe> recipeList = new ArrayList<>();
}
