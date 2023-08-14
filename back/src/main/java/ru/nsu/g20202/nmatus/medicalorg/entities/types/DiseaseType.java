package ru.nsu.g20202.nmatus.medicalorg.entities.types;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "disease_type")
public class DiseaseType {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "disease_type")
    private String type;
}
