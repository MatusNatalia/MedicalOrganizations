package ru.nsu.g20202.nmatus.medicalorg.entities.clinic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "cabinet")
public class Cabinet {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "number")
    @NotNull
    private String number;

    @ManyToOne(optional = false)
    private Clinic clinic;

    @OneToMany(mappedBy = "cabinet")
    private List<Specialist> specialists;
}
