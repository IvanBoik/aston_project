package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Float sum; //в миграции покорректировать название
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusTransaction statusId;

    private Integer orderId;
    private Integer fromId;
    private Integer toId;


}
