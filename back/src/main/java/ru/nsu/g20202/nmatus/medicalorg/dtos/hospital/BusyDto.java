package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

@Data
public class BusyDto {
    private String specialist;
    private String hospital;
    private String type;
    private Integer patients;
}
