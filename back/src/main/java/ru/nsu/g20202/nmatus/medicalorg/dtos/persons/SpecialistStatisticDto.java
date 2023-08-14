package ru.nsu.g20202.nmatus.medicalorg.dtos.persons;

import lombok.Data;

@Data
public class SpecialistStatisticDto {
    private String specialistName;
    private Double avgPatients;
}
