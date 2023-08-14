package ru.nsu.g20202.nmatus.medicalorg.dtos.persons;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;

import java.util.List;

@Getter
@Setter
public class SpecialistTypeDto {
    private Integer id;
    private String type;
}
