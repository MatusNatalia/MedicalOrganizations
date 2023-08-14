package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.VisitDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.StudyDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Visit;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.StudyMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyService {

    private StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository){
        this.studyRepository = studyRepository;
    }

    @Transactional
    public void addStudy(StudyDto studyDto) {
        try {
            studyRepository.save(StudyMapper.toEntity(studyDto));
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
