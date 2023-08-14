package ru.nsu.g20202.nmatus.medicalorg.entities.persons;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Department;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Treatment;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Study;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "patient")
public class Patient extends Person{

    @ManyToOne()
    private Clinic clinic;

    @Column(name = "phone")
    @Pattern(regexp = "^[0-9]{7,11}$", message = "Несуществующий телефонный номер")
    private String phone;

    @Column(name = "address")
    @NotBlank(message = "Пустой адрес")
    @Size(min = 1, max = 255, message = "Адрес должен содержать 1-255 символов")
    private String address;

    @OneToMany(mappedBy = "patient")
    private List<Treatment> treatments;

    @OneToMany(mappedBy = "patient")
    private List<Study> studies;
}

