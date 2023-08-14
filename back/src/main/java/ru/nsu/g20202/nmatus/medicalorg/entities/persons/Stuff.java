package ru.nsu.g20202.nmatus.medicalorg.entities.persons;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.StuffType;

@Getter
@Setter
@Entity
@Table(name= "stuff")
public class Stuff extends Person{
    @ManyToOne(optional = false)
    private StuffType type;

    @ManyToOne()
    private Organization organization;
}
