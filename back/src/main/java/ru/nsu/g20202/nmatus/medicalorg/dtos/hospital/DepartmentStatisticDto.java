package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

@Data
public class DepartmentStatisticDto {
    private String name;
    private Integer freeRoomNumber;
    private Integer freeBedNumber;
}
