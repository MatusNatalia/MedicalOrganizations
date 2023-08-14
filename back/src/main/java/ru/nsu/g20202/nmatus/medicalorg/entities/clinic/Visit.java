package ru.nsu.g20202.nmatus.medicalorg.entities.clinic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;

@Getter
@Setter
@Entity
@Table(name= "visit")
public class Visit {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(optional = false)
    private Cabinet cabinet;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private Specialist specialist;

    @Column(name = "time")
    private String date;

}
