package ru.nsu.g20202.nmatus.medicalorg.entities.types;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Operation;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "specialist_type")
public class SpecialistType {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "specialist_type")
    private String type;

    @OneToMany(mappedBy = "type")
    private List<Specialist> specialists;
}
