package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.VisitDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Visit;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.PatientRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.SpecialistRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.VisitRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitService {

    private VisitRepository visitRepository;
    private PatientRepository patientRepository;
    private SpecialistRepository specialistRepository;

    public VisitService(VisitRepository visitRepository, PatientRepository patientRepository,
                        SpecialistRepository specialistRepository){
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.specialistRepository = specialistRepository;
    }

    public void addVisit(VisitDto visitDto) {
        try {
            visitRepository.save(this.toEntity(visitDto));
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

    public VisitDto toDto(Visit visit){
        VisitDto dto = new VisitDto();
        dto.setPatient(visit.getPatient().getId());
        dto.setSpecialist(visit.getSpecialist().getId());
        dto.setDate(visit.getDate());
        return dto;
    }

    public Visit toEntity(VisitDto dto){
        Visit entity = new Visit();
        if(dto.getPatient() == null || dto.getSpecialist() == null){
            List<String> errors = new ArrayList<>();
            errors.add("Заполните все поля");
            throw new IllegalValueException(errors);
        }
        entity.setPatient(patientRepository.findById(dto.getPatient()).get());
        entity.setSpecialist(specialistRepository.findById(dto.getSpecialist()).get());
        entity.setCabinet(specialistRepository.findById(dto.getSpecialist()).get().getCabinet());
        if(!dto.getDate().matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        entity.setDate(dto.getDate());
        return entity;
    }

}
