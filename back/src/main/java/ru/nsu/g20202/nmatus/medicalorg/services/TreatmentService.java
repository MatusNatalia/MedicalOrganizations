package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.TreatmentDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Treatment;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.TreatmentMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final SpecialistRepository specialistRepository;

    public TreatmentService(TreatmentRepository treatmentRepository,
                            SpecialistRepository specialistRepository){
        this.treatmentRepository = treatmentRepository;
        this.specialistRepository = specialistRepository;
    }

    public List<TreatmentDto> getTreatmentsByOrganization(Integer organization){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
        for (Treatment treatment : treatmentRepository.getTreatmentByOrganization(organization)) {
            treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }

    public List<TreatmentDto> getOldTreatmentsByOrganization(Integer organization, String start, String end){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
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
        for (Treatment treatment : treatmentRepository.getOldTreatmentByOrganization(organization, start, end)) {
            treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }
    public List<TreatmentDto> getOldTreatmentsBySpecialist(Integer specialist, String start, String end){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
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
        for (Treatment treatment : treatmentRepository.getOldTreatmentBySpecialist(specialist, start, end)) {
                treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }


    public List<TreatmentDto> getTreatmentsByDepartment(Integer department){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
        for (Treatment treatment : treatmentRepository.getTreatmentByDepartment(department)) {
            treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }

    public List<TreatmentDto> getTreatmentsByRoom(Integer room){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
        for (Treatment treatment : treatmentRepository.getTreatmentByRoom(room)) {
            treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }

    public List<TreatmentDto> getTreatmentsBySpecialist(Integer specialistId){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
        for(Treatment treatment : treatmentRepository.getTreatmentsBySpecialist(specialistId)){
            treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }

    public List<TreatmentDto> getTreatmentsBySpecialistType(Integer typeId){
        List<TreatmentDto> treatmentDtos = new ArrayList<>();
        for(Treatment treatment : treatmentRepository.getTreatmentsBySpecialistType(typeId)){
            treatmentDtos.add(TreatmentMapper.toDto(treatment));
        }
        return treatmentDtos;
    }

    @Transactional
    public void addTreatment(TreatmentDto treatmentDto) {
        try {
            treatmentRepository.save(TreatmentMapper.toEntity(treatmentDto));
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

    @Transactional
    public void updateTreatment(TreatmentDto treatmentDto) {
        try {
            treatmentRepository.save(TreatmentMapper.toEntity(treatmentDto));
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

}
