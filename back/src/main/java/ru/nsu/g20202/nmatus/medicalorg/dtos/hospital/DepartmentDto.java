package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

@Data
public class DepartmentDto {
    private Integer id;
    private String name;
    private Integer block;
    private String type;
}
