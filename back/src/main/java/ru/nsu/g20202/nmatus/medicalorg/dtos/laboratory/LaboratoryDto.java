package ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory;

import lombok.Data;
import ru.nsu.g20202.nmatus.medicalorg.dtos.organizations.OrganizationDto;

@Data
public class LaboratoryDto extends OrganizationDto {
    private String type;
}
