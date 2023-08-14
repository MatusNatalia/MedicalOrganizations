package ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory;

import lombok.Data;

@Data
public class StudyDto {
    private Integer patient;
    private Integer laboratory;
    private String date;
}
