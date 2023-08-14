package ru.nsu.g20202.nmatus.medicalorg.entities.types;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "stuff_type")
public class StuffType {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "stuff_type")
    private String type;
}
