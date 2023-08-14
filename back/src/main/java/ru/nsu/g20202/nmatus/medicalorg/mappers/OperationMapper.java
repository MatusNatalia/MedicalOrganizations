package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.OperationDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.StudyDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Operation;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Study;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperationMapper {
    private static TreatmentRepository treatmentRepository;
    private static SpecialistRepository specialistRepository;

    @Autowired
    public OperationMapper(TreatmentRepository treatmentRepository,
                           SpecialistRepository specialistRepository){
        OperationMapper.treatmentRepository = treatmentRepository;
        OperationMapper.specialistRepository = specialistRepository;
    }

    public static OperationDto toDto(Operation operation){
        OperationDto operationDto = new OperationDto();
        operationDto.setDate(operation.getDate());
        operationDto.setSpecialist(operation.getSpecialist().getId());
        operationDto.setTreatment(operation.getTreatment().getId());
        operationDto.setPatientName(operation.getTreatment().getPatient().getName()+" "+operation.getTreatment().getPatient().getSurname());
        operationDto.setSpecialistName(operation.getSpecialist().getName()+" "+operation.getSpecialist().getSurname());
        return operationDto;
    }
    public static Operation toEntity(OperationDto operationDto){
        Operation operation = new Operation();
        if(!operationDto.getDate().matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        operation.setDate(operationDto.getDate());
        if (!treatmentRepository.existsById(operationDto.getTreatment())){
            List<String> msg = new ArrayList<>();
            msg.add("Выберите пациента");
            throw new IllegalValueException(msg);
        }
        operation.setTreatment(treatmentRepository.findById(operationDto.getTreatment()).get());
        if (!specialistRepository.existsById(operationDto.getSpecialist())){
            List<String> msg = new ArrayList<>();
            msg.add("Выберите специалиста");
            throw new IllegalValueException(msg);
        }
        operation.setSpecialist(specialistRepository.findById(operationDto.getSpecialist()).get());
        return operation;
    }
}
