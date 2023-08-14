package ru.nsu.g20202.nmatus.medicalorg.dtos.clinic;

import lombok.Data;

@Data
public class CabinetStatisticDto {
    private String number;
    private Integer visits;
}
