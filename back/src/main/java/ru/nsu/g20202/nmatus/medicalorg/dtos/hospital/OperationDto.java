package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

@Data
public class OperationDto {
    private Integer treatment;
    private Integer specialist;
    private String patientName;
    private String specialistName;
    private String date;
}
