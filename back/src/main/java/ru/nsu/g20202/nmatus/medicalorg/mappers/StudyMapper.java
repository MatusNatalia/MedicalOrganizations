package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.StudyDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Study;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudyMapper {

    private static LaboratoryRepository laboratoryRepository;
    private static PatientRepository patientRepository;
    private static OrganizationRepository organizationRepository;

    @Autowired
    public StudyMapper(LaboratoryRepository laboratoryRepository,
                       PatientRepository patientRepository,
                       OrganizationRepository organizationRepository){
        StudyMapper.laboratoryRepository = laboratoryRepository;
        StudyMapper.patientRepository = patientRepository;
        StudyMapper.organizationRepository = organizationRepository;
    }

    public static StudyDto toDto(Study study){
        StudyDto studyDto = new StudyDto();
        studyDto.setLaboratory(study.getLaboratory().getId());
        studyDto.setPatient(study.getPatient().getId());
        studyDto.setDate(study.getDate());
        return studyDto;
    }
    public static Study toEntity(StudyDto studyDto){
        Study study = new Study();
        study.setLaboratory(laboratoryRepository.findById(studyDto.getLaboratory()).get());
        Patient patient = patientRepository.findById(studyDto.getPatient()).get();
        study.setPatient(patient);
        study.setOrganization(organizationRepository.findById(patient.getClinic().getId()).get());
        if(!studyDto.getDate().matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        study.setDate(studyDto.getDate());
        return study;
    }
}
