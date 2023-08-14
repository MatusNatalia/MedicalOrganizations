package ru.nsu.g20202.nmatus.medicalorg.dtos.clinic;

import lombok.Data;

import java.util.List;

@Data
public class CabinetDto {
    private String number;
    private String clinic;
    private List<String> specialists;
}
