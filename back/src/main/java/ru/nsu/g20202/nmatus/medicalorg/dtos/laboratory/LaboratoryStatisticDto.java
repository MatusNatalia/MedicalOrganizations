package ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory;

import lombok.Data;

@Data
public class LaboratoryStatisticDto {
    private String laboratory;
    private Integer avgStudies;
}
