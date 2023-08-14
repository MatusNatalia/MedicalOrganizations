package ru.nsu.g20202.nmatus.medicalorg.dtos.persons;

import lombok.Data;

@Data
public class SpecialistDto extends PersonDto{
    private String organization;
    private String cabinet;
    private String type;
    private Integer experience;
    private String title;
    private String degree;
}

