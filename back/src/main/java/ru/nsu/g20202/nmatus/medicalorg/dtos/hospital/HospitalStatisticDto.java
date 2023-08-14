package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;

import java.util.List;

@Data
public class HospitalStatisticDto {
    private Integer totalRoomNumber;
    private Integer totalBedNumber;
    private List<DepartmentStatisticDto> departmentStatistic;
}
