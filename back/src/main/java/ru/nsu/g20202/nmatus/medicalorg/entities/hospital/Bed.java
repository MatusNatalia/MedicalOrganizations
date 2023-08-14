package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "bed")
public class Bed {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "busy")
    @NotNull
    private Boolean busy;

    @ManyToOne(optional = false)
    private Room room;
}
