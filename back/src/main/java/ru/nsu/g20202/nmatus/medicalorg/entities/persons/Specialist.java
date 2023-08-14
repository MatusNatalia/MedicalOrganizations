package ru.nsu.g20202.nmatus.medicalorg.entities.persons;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Cabinet;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Operation;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Treatment;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.SpecialistType;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "specialist")
public class Specialist extends Person{

    @ManyToOne
    @NotNull(message = "Выберите организацию")
    private Organization organization;

    @ManyToOne
    private Cabinet cabinet;

    @ManyToOne(optional = false)
    private SpecialistType type;

    @Column(name = "experience")
    @Max(50)
    @NotNull(message = "Введите стаж работы")
    private Integer experience;

    @Column(name = "title")
    @NotBlank(message = "Введите звание")
    private String title;

    @Column(name = "degree")
    @NotBlank(message = "Введите степень")
    private String degree;

    @OneToMany(mappedBy = "specialist")
    private List<Treatment> treatments;

    @OneToMany(mappedBy = "specialist")
    private List<Operation> operations;

    public Specialist() {
        super();
    }

}

