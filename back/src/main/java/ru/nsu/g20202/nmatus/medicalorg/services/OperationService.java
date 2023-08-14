package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.OperationDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Operation;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.OperationMapper;
import ru.nsu.g20202.nmatus.medicalorg.mappers.SpecialistMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.OperationRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.SpecialistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OperationService {

    private OperationRepository operationRepository;
    private SpecialistRepository specialistRepository;

    public OperationService(OperationRepository operationRepository,
                            SpecialistRepository specialistRepository){
        this.operationRepository = operationRepository;
        this.specialistRepository = specialistRepository;
    }

    @Transactional
    public void addOperation(OperationDto operationDto) {
        try {
            operationRepository.save(OperationMapper.toEntity(operationDto));
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

    public List<OperationDto> getOperations(Integer hospital, Integer specialist, String start, String end){
        List<OperationDto> operationDtos = new ArrayList<>();
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
        if(specialist == 0) {
            for (Operation operation : operationRepository.getOperationsByHospital(hospital, start, end)) {
                operationDtos.add(OperationMapper.toDto(operation));
            }
        }
        else{
            for (Operation operation : operationRepository.getOperationsBySpecialist(specialist, start, end)) {
                operationDtos.add(OperationMapper.toDto(operation));
            }
        }
        return operationDtos;
    }
}
