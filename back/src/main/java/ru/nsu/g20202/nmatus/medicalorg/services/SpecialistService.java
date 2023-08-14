package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistTypeDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Laboratory;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.SpecialistType;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.SpecialistMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.*;

@Service
public class SpecialistService {

    private SpecialistRepository specialistRepository;
    private OrganizationRepository organizationRepository;
    private SpecialistTypeRepository specialistTypeRepository;
    private HospitalRepository hospitalRepository;
    private ClinicRepository clinicRepository;

    public SpecialistService(SpecialistRepository specialistRepository,
                             OrganizationRepository organizationRepository,
                             SpecialistTypeRepository specialistTypeRepository,
                             HospitalRepository hospitalRepository,
                             ClinicRepository clinicRepository) {
        this.specialistRepository = specialistRepository;
        this.organizationRepository = organizationRepository;
        this.specialistTypeRepository = specialistTypeRepository;
        this.hospitalRepository = hospitalRepository;
        this.clinicRepository = clinicRepository;
    }

    public List<SpecialistDto> getAllSpecialists(){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        for(Specialist specialist : specialistRepository.findAll()){
            specialistDtos.add(SpecialistMapper.toDto(specialist));
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getHospitalSpecialists(){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        for(Specialist specialist : specialistRepository.findAll()){
            if(hospitalRepository.existsById(specialist.getOrganization().getId())) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getClinicSpecialists(){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        for(Specialist specialist : specialistRepository.findAll()){
            if(clinicRepository.existsById(specialist.getOrganization().getId())) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getSpecialistsByOrganization(Integer organization){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        for (Specialist specialist : specialistRepository.getSpecialistsByOrganization(organization)) {
            specialistDtos.add(SpecialistMapper.toDto(specialist));
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getSpecialistsByType(String type, String organization){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        if(Objects.equals(organization, "0")) {
            for (Specialist specialist : specialistRepository.findByType(specialistTypeRepository.findByType(type))) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        else{
            for(Specialist specialist : specialistRepository.getByTypeAndOrganization(specialistTypeRepository.findByType(type).getId(), organizationRepository.findByName(organization).getId())){
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getSpecialistsByTypeAndExperience(String type, String organization, Integer experience){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        Integer typeId = specialistTypeRepository.findByType(type).getId();
        if(organization.equals("0")) {
            for (Specialist specialist : specialistRepository.getByTypeAndExperience(typeId, experience)) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        else{
            Integer organizationId = organizationRepository.findByName(organization).getId();
            for(Specialist specialist : specialistRepository.getByTypeAndExperienceAndOrganization(typeId, organizationId, experience)){
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getSpecialistsWithDegreeByType(String type, String organization, String degree, String title){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        Integer typeId = specialistTypeRepository.findByType(type).getId();
        if(organization.equals("0")) {
            for (Specialist specialist : specialistRepository.getByTypeAndDegreeAndTitle(typeId, degree, title)) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        else{
            Integer organizationId = organizationRepository.findByName(organization).getId();
            for(Specialist specialist : specialistRepository.getByTypeAndDegreeAndTitleAndOrganization(typeId, organizationId, degree, title)){
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        return specialistDtos;
    }

    public List<SpecialistDto> getSpecialistsByTypeAndOperations(String type, String organization, Integer operations){
        List<SpecialistDto> specialistDtos = new ArrayList<>();
        Integer typeId = specialistTypeRepository.findByType(type).getId();
        if(organization.equals("0")) {
            for (Specialist specialist : specialistRepository.getByTypeAndOperations(typeId, operations)) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        else {
            Integer organizationId = organizationRepository.findByName(organization).getId();
            for (Specialist specialist : specialistRepository.getByTypeAndOperationsAndOrganization(typeId, organizationId, operations)) {
                specialistDtos.add(SpecialistMapper.toDto(specialist));
            }
        }
        return specialistDtos;
    }

    public Iterable<SpecialistTypeDto> getTypes(){
        List<SpecialistTypeDto> dtos = new ArrayList<>();
        for(SpecialistType specialistType : specialistTypeRepository.findAll()){
            SpecialistTypeDto specialistTypeDto = new SpecialistTypeDto();
            specialistTypeDto.setId(specialistType.getId());
            specialistTypeDto.setType(specialistType.getType());
            dtos.add(specialistTypeDto);
        }
        return dtos;
    }

    public List<SpecialistStatisticDto> getSpecialistStatisticByClinic(Integer clinicId, String start, String end){
        List<SpecialistStatisticDto> dtos = new ArrayList<>();
        checkDate(start, end);
        for(Specialist specialist : clinicRepository.findById(clinicId).get().getSpecialists()){
            SpecialistStatisticDto dto = new SpecialistStatisticDto();
            dto.setSpecialistName(specialist.getName()+" "+specialist.getSurname());
            Double avg = specialistRepository.getAvgPatients(specialist.getId(), start, end);
            if(avg == null){
                dto.setAvgPatients(0.0);
            }
            else {
                dto.setAvgPatients(avg);
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public List<SpecialistStatisticDto> getSpecialistStatisticByType(Integer typeId, String start, String end){
        List<SpecialistStatisticDto> dtos = new ArrayList<>();
        checkDate(start, end);
        for(Specialist specialist : specialistTypeRepository.findById(typeId).get().getSpecialists()){
            SpecialistStatisticDto dto = new SpecialistStatisticDto();
            dto.setSpecialistName(specialist.getName()+" "+specialist.getSurname());
            Double avg = specialistRepository.getAvgPatients(specialist.getId(), start, end);
            if(avg == null){
                dto.setAvgPatients(0.0);
            }
            else {
                dto.setAvgPatients(avg);
            }
            dtos.add(dto);
        }
        return dtos;
    }
    public List<SpecialistStatisticDto> getSpecialistStatisticById(Integer specialistId, String start, String end){
        SpecialistStatisticDto dto = new SpecialistStatisticDto();
        checkDate(start, end);
        Specialist specialist = specialistRepository.findById(specialistId).get();
        dto.setSpecialistName(specialist.getName()+" "+specialist.getSurname());
        Double avg = specialistRepository.getAvgPatients(specialistId, start, end);
        if(avg == null){
            dto.setAvgPatients(0.0);
        }
        else {
            dto.setAvgPatients(avg);
        }
        List<SpecialistStatisticDto> dtos = new ArrayList<>();
        dtos.add(dto);
        return dtos;
    }

    private void checkDate(String start, String end) {
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
    }


    public void addSpecialist(SpecialistDto specialistDto) {
        try {
            specialistRepository.save(SpecialistMapper.toEntity(specialistDto));
        } catch(TransactionSystemException e){
            if (e.getRootCause() instanceof ConstraintViolationException) {
                List<String> errors = new ArrayList<>();
                ConstraintViolationException exception = (ConstraintViolationException) e.getRootCause();
                for(ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()){
                    errors.add(constraintViolation.getMessage());
                }
                throw new IllegalValueException(errors);
            }
        }
    }

    public void updateSpecialist(SpecialistDto specialistDto) {
        try {
            specialistRepository.save(SpecialistMapper.toEntity(specialistDto));
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

    public void deleteSpecialist(Integer id){
        specialistRepository.deleteById(id);
    }

}
