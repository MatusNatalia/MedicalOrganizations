package ru.nsu.g20202.nmatus.medicalorg.dtos.hospital;

import lombok.Data;
import ru.nsu.g20202.nmatus.medicalorg.dtos.organizations.OrganizationDto;

@Data
public class RoomDto extends OrganizationDto {
    private Integer id;
    private String number;
    private Integer size;
    private Integer busy;
    private Integer department;
}
