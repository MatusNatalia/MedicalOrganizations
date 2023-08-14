package ru.nsu.g20202.nmatus.medicalorg.entities.types;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "laboratory_type")
public class LaboratoryType {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "laboratory_type")
    @NotBlank
    private String type;
}
