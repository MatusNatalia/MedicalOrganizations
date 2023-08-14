package ru.nsu.g20202.nmatus.medicalorg.dtos.clinic;

import lombok.Data;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.HospitalDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.HospitalStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.organizations.OrganizationDto;

@Data
public class ClinicDto extends OrganizationDto {
    private String address;
    private HospitalDto hospital;
}
