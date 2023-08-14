package ru.nsu.g20202.nmatus.medicalorg.dtos.clinic;

import lombok.Data;
import java.util.List;

@Data
public class ClinicStatisticDto {
    private Integer cabinetNumber;
    private List<CabinetStatisticDto> cabinetStatistic;
}
