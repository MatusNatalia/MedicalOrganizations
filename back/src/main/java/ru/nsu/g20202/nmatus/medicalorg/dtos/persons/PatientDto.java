package ru.nsu.g20202.nmatus.medicalorg.dtos.persons;

import lombok.Data;

@Data
public class PatientDto extends PersonDto {
    private String clinic;
    private String phone;
    private String address;
}
