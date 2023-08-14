package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.PatientDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.ClinicRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.OrganizationRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.PatientRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.SpecialistTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private OrganizationRepository organizationRepository;
    private ClinicRepository clinicRepository;
    private SpecialistTypeRepository specialistTypeRepository;

    public PatientService(PatientRepository patientRepository, OrganizationRepository organizationRepository,
                          ClinicRepository clinicRepository, SpecialistTypeRepository specialistTypeRepository) {
        this.patientRepository = patientRepository;
        this.organizationRepository = organizationRepository;
        this.clinicRepository = clinicRepository;
        this.specialistTypeRepository = specialistTypeRepository;
    }

    public List<PatientDto> getAllPatients(){
        List<PatientDto> patientDtos = new ArrayList<>();
        for(Patient patient : patientRepository.findAll()){
            patientDtos.add(this.toDto(patient));
        }
        return patientDtos;
    }

    public List<PatientDto> getPatientsByClinic(String clinicName){
        List<PatientDto> patientDtos = new ArrayList<>();
        if(!clinicRepository.existsByName(clinicName)){
            List<String> errors = new ArrayList<>();
            errors.add("Выберите поликлинику");
            throw new IllegalValueException(errors);
        }
        Clinic clinic = clinicRepository.findByName(clinicName).get(0);
        for(Patient patient : patientRepository.findByClinic(clinic.getId())){
            patientDtos.add(this.toDto(patient));
        }
        return patientDtos;
    }

    public List<PatientDto> getPatientsBySpecialistType(String type, String organization){
        List<PatientDto> patientDtos = new ArrayList<>();
        Integer typeId = specialistTypeRepository.findByType(type).getId();
        if(Objects.equals(organization, "0")) {
            for (Patient patient : patientRepository.findBySpecialistType(typeId)) {
                patientDtos.add(this.toDto(patient));
            }
        }
        else{
            Integer organizationId = organizationRepository.findByName(organization).getId();
            for (Patient patient : patientRepository.findBySpecialistTypeAndOrganization(typeId, organizationId)) {
                patientDtos.add(this.toDto(patient));
            }
        }
        return patientDtos;
    }
    public void addPatient(PatientDto patientDto) {
        try {
            patientRepository.save(this.toEntity(patientDto));
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

    public void updatePatient(PatientDto patientDto) {
        try {
            patientRepository.save(this.toEntity(patientDto));
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

    public void deletePatient(Integer id){
        patientRepository.deleteById(id);
    }

    public PatientDto toDto(Patient patient){
        PatientDto dto = new PatientDto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setSurname(patient.getSurname());
        dto.setPhone(patient.getPhone());
        dto.setAddress(patient.getAddress());
        dto.setClinic(patient.getClinic().getName());
        return dto;
    }

    public Patient toEntity(PatientDto dto){
        Patient entity;
        if(dto.getId() != null){
            entity = patientRepository.findById(dto.getId()).get();
        }
        else {
            entity = new Patient();
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        if(!clinicRepository.existsByName(dto.getClinic())){
            List<String> errors = new ArrayList<>();
            errors.add("Выберите поликлинику");
            throw new IllegalValueException(errors);
        }
        entity.setClinic(clinicRepository.findByName(dto.getClinic()).get(0));
        return entity;
    }

}
