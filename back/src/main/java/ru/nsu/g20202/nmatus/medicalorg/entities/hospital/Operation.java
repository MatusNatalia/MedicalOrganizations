package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;


@Getter
@Setter
@Entity
@Table(name= "operation")
public class Operation {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(optional = false)
    private Treatment treatment;

    @ManyToOne(optional = false)
    private Specialist specialist;

    @Column(name = "date")
    private String date;
}
