package ru.nsu.g20202.nmatus.medicalorg.entities.laboratory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name= "study")
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    private Laboratory laboratory;

    @ManyToOne()
    private Organization organization;

    @ManyToOne
    private Patient patient;

    @Column(name = "time")
    private String date;
}
