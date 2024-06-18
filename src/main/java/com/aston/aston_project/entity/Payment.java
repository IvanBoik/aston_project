package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDate date;
    @UpdateTimestamp
    private LocalTime time;
    private BigDecimal sum; //в миграции покорректировать название

    @Enumerated(EnumType.STRING)
    private StatusTransaction statusId;

    private Integer orderId;
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromId;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private Pharmacy toId;


}
