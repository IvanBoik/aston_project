package com.aston.aston_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = "id")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String house;

    private String street;

    private Short room;

    public String getFullAddress(){
        return String.format("%s, %s, %s, %d", city,street,house,room);
    }
}
