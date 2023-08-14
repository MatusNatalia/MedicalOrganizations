package ru.nsu.g20202.nmatus.medicalorg.dtos.persons;

import lombok.Data;

@Data
public class StuffDto extends PersonDto {
    private String organization;
    private String type;
}
