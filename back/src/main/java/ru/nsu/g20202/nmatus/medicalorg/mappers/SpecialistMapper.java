package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.OperationDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Cabinet;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Operation;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SpecialistMapper {
    private static SpecialistTypeRepository specialistTypeRepository;
    private static OrganizationRepository organizationRepository;
    private static CabinetRepository cabinetRepository;
    private static ClinicRepository clinicRepository;

    @Autowired
    public SpecialistMapper(SpecialistTypeRepository specialistTypeRepository,
                            OrganizationRepository organizationRepository,
                            ClinicRepository clinicRepository,
                            CabinetRepository cabinetRepository){
        SpecialistMapper.specialistTypeRepository = specialistTypeRepository;
        SpecialistMapper.cabinetRepository = cabinetRepository;
        SpecialistMapper.clinicRepository = clinicRepository;
        SpecialistMapper.organizationRepository = organizationRepository;
    }

    public static SpecialistDto toDto(Specialist specialist){
        SpecialistDto dto = new SpecialistDto();
        dto.setId(specialist.getId());
        dto.setName(specialist.getName());
        dto.setSurname(specialist.getSurname());
        dto.setType(specialist.getType().getType());
        dto.setExperience(specialist.getExperience());
        dto.setTitle(specialist.getTitle());
        dto.setDegree(specialist.getDegree());
        if(specialist.getCabinet() != null){
            dto.setCabinet(specialist.getCabinet().getNumber());
        }
        dto.setOrganization(specialist.getOrganization().getName());
        return dto;
    }

    public static Specialist toEntity(SpecialistDto dto){
        List<String> errors = new ArrayList<>();
        Specialist entity = new Specialist();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        switch(dto.getTitle()){
            case "доцент" : {
                if(!Objects.equals(dto.getDegree(), "кандидат")){
                    errors.add("Звание доцента присваивается только кандидатам мед. наук");
                    throw new IllegalValueException(errors);
                }
                break;
            }
            case "профессор" : {
                if (!Objects.equals(dto.getDegree(), "доктор")) {
                    errors.add("Звание профессора присваивается только докторам мед. наук");
                    throw new IllegalValueException(errors);
                }
                break;
            }
            default : break;
        }
        entity.setTitle(dto.getTitle());
        entity.setDegree(dto.getDegree());
        entity.setExperience(dto.getExperience());
        if(dto.getType() == null || dto.getOrganization() == null){
            errors.add("Заполните все поля");
            throw new IllegalValueException(errors);
        }
        entity.setType(specialistTypeRepository.findByType(dto.getType()));
        if(clinicRepository.existsByName(dto.getOrganization())) {
            Cabinet cabinet = cabinetRepository.findByNumber(dto.getCabinet());
            if (cabinet == null || !cabinet.getClinic().getName().equals(dto.getOrganization())) {
                errors.add("Несуществующий кабинет");
                throw new IllegalValueException(errors);
            }
            entity.setCabinet(cabinetRepository.findByNumber(dto.getCabinet()));
        }
        entity.setOrganization(organizationRepository.findByName(dto.getOrganization()));
        return entity;
    }
}
