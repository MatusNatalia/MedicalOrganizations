package ru.nsu.g20202.nmatus.medicalorg.entities.clinic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Hospital;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name= "clinic")
public class Clinic extends Organization {
    @ManyToOne
    private Hospital hospital;
    @Column(name = "address")
    @NotBlank(message = "Пустой адрес")
    @Size(min = 1, max = 255, message = "Адрес должен содержать 1-255 символов")
    private String address;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.REMOVE)
    private List<Cabinet> cabinets;

    @OneToMany(mappedBy = "clinic")
    private List<Patient> patients;

    public Clinic() {
        super();
    }
}