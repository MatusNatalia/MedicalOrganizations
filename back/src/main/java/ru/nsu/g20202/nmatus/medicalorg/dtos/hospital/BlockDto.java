package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

@Data
public class BlockDto {
    private Integer id;
    private String name;
    private String address;
    private String hospital;
}
