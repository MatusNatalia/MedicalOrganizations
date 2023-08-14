package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;
import ru.nsu.g20202.nmatus.medicalorg.dtos.organizations.OrganizationDto;

import java.util.List;

@Data
public class HospitalDto extends OrganizationDto {
    private List<String> addresses;
    private List<String> blocks;
    private List<String> departments;
}
