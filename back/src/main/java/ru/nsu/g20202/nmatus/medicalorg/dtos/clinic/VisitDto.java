package ru.nsu.g20202.nmatus.medicalorg.dtos.clinic;

import lombok.Data;

@Data
public class VisitDto {
    private Integer patient;
    private Integer specialist;
    private String date;
}
