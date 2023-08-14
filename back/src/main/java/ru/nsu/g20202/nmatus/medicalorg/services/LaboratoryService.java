package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Laboratory;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.LaboratoryType;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.LaboratoryRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.LaboratoryTypeRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.OrganizationRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.StudyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LaboratoryService {
    private final StudyRepository studyRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final OrganizationRepository organizationRepository;
    private final LaboratoryTypeRepository laboratoryTypeRepository;

    public LaboratoryService(StudyRepository studyRepository, LaboratoryRepository laboratoryRepository,
                             OrganizationRepository organizationRepository,
                             LaboratoryTypeRepository laboratoryTypeRepository){
        this.studyRepository = studyRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.organizationRepository = organizationRepository;
        this.laboratoryTypeRepository = laboratoryTypeRepository;
    }

    public List<LaboratoryDto> getAllLaboratories(){
        List<LaboratoryDto> laboratoryDtoList = new ArrayList<>();
        for(Laboratory laboratory : laboratoryRepository.findAll()){
            laboratoryDtoList.add(this.toDto(laboratory));
        }
        return laboratoryDtoList;
    }

    public List<LaboratoryStatisticDto> getStatistic(String organization, String start, String end){
        List<LaboratoryStatisticDto> dtos = new ArrayList<>();
        if(!start.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        if(!end.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        if(Objects.equals(organization, "0")) {
            for(Laboratory laboratory : laboratoryRepository.findAll()){
                LaboratoryStatisticDto dto = new LaboratoryStatisticDto();
                dto.setLaboratory(laboratory.getName());
                if(studyRepository.getAvgStudies(laboratory.getId(), start, end) == null){
                    dto.setAvgStudies(0);
                }
                else {
                    dto.setAvgStudies(studyRepository.getAvgStudies(laboratory.getId(), start, end));
                }
                dtos.add(dto);
            }
        }
        else{
            Organization org = organizationRepository.findByName(organization);
            for(Laboratory laboratory : org.getLaboratories()){
                LaboratoryStatisticDto dto = new LaboratoryStatisticDto();
                dto.setLaboratory(laboratory.getName());
                if(studyRepository.getAvgStudies(laboratory.getId(), start, end) == null){
                    dto.setAvgStudies(0);
                }
                else {
                    dto.setAvgStudies(studyRepository.getAvgStudies(laboratory.getId(), start, end));
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public Iterable<LaboratoryType> getLaboratoryTypes(){
        return laboratoryTypeRepository.findAll();
    }

    public void addLaboratory(LaboratoryDto dto){
        try {
            laboratoryRepository.saveAndFlush(this.toEntity(dto));
        } catch(TransactionSystemException e){
            if (e.getRootCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException) e.getRootCause();
                List<String> errors = new ArrayList<>();
                for(ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()){
                    errors.add(constraintViolation.getMessage());
                }
                throw new IllegalValueException(errors);
            }
        }
    }

    public void updateLaboratory(LaboratoryDto dto) {
        try {
            laboratoryRepository.saveAndFlush(this.toEntity(dto));
        } catch(TransactionSystemException e){
            if (e.getRootCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException) e.getRootCause();
                List<String> errors = new ArrayList<>();
                for(ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()){
                    errors.add(constraintViolation.getMessage());
                }
                throw new IllegalValueException(errors);
            }
        }
    }

    public void deleteLaboratory(Integer id){
        laboratoryRepository.deleteById(id);
    }

    public LaboratoryDto toDto(Laboratory organization){
        LaboratoryDto dto = new LaboratoryDto();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setNumber(organization.getNumber());
        dto.setType(organization.getType().getType());
        return dto;
    }

    public Laboratory toEntity(LaboratoryDto laboratoryDto){
        Laboratory laboratory;
        if(laboratoryDto.getId() != null && laboratoryRepository.existsById(laboratoryDto.getId())){
            laboratory = laboratoryRepository.findById(laboratoryDto.getId()).get();
        }
        else {
            laboratory = new Laboratory();
        }
        laboratory.setName(laboratoryDto.getName());
        laboratory.setNumber(laboratoryDto.getNumber());
        laboratory.setType(laboratoryTypeRepository.findByType(laboratoryDto.getType()));
        return laboratory;
    }


}

