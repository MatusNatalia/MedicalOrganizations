package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "state")
public class State {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "state_type")
    private String state;
}
