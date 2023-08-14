package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

@Data
public class TreatmentDto {

    private Integer id;
    private Integer patient;
    private String patientName;
    private String enterDate;
    private String checkOutDate;
    private Integer specialist;
    private String specialistName;
    private Integer room;
    private String roomNumber;
    private Double temp;
    private String state;

}
