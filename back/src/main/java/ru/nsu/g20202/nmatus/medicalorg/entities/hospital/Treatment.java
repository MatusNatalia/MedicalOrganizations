package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name= "treatment")
public class Treatment {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne()
    private Patient patient;

    @Column(name = "enter_date")
    private String enterDate;

    @Column(name = "check_out_date")
    private String checkOutDate;

    @ManyToOne()
    private Room room;

    @ManyToOne()
    private Specialist specialist;

    @Column(name = "temp")
    @NotNull(message = "Уточните температуру пациента")
    private Double temp;

    @Column(name = "state")
    @NotBlank(message = "Уточните состояние пациента")
    private String state;

}
