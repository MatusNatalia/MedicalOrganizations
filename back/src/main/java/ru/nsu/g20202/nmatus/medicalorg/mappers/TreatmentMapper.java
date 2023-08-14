package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.RoomDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.TreatmentDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Room;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Treatment;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.DepartmentRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.PatientRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.RoomRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.SpecialistRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class TreatmentMapper {
    private static PatientRepository patientRepository;
    private static SpecialistRepository specialistRepository;
    private static RoomRepository roomRepository;

    @Autowired
    public TreatmentMapper(PatientRepository patientRepository,
                           SpecialistRepository specialistRepository,
                           RoomRepository roomRepository){
        TreatmentMapper.specialistRepository = specialistRepository;
        TreatmentMapper.patientRepository = patientRepository;
        TreatmentMapper.roomRepository = roomRepository;
    }

    public static Treatment toEntity(TreatmentDto dto){
        Treatment entity = new Treatment();
        if(dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (!patientRepository.existsById(dto.getPatient())){
            List<String> msg = new ArrayList<>();
            msg.add("Выберите пациента");
            throw new IllegalValueException(msg);
        }
        entity.setPatient(patientRepository.findById(dto.getPatient()).get());
        if (!specialistRepository.existsById(dto.getSpecialist())){
            List<String> msg = new ArrayList<>();
            msg.add("Выберите специалиста");
            throw new IllegalValueException(msg);
        }
        entity.setSpecialist(specialistRepository.findById(dto.getSpecialist()).get());
        if (!roomRepository.existsById(dto.getRoom())){
            List<String> msg = new ArrayList<>();
            msg.add("Выберите палату");
            throw new IllegalValueException(msg);
        }
        Room room = roomRepository.findById(dto.getRoom()).get();
        if(dto.getId() == null) {
            if(room.getSize() - room.getBusy() <= 0){
                List<String> msg = new ArrayList<>();
                msg.add("В выбранной палате нет свободных коек");
                throw new IllegalValueException(msg);
            }
            room.setBusy(room.getBusy() + 1);
            room.setFree(false);
        }
        entity.setRoom(room);
        if(!dto.getEnterDate().matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        if(dto.getCheckOutDate() != null){
            if(!dto.getCheckOutDate().matches("^\\d{4}-\\d{2}-\\d{2}$")){
                List<String> msg = new ArrayList<>();
                msg.add("Введите дату в формате ГГГГ-ММ-ДД");
                throw new IllegalValueException(msg);
            }
            entity.setCheckOutDate(dto.getCheckOutDate());
        }
        entity.setEnterDate(dto.getEnterDate());
        entity.setTemp(dto.getTemp());
        entity.setState(dto.getState());
        return entity;
    }

    public static TreatmentDto toDto(Treatment treatment){
        TreatmentDto treatmentDto = new TreatmentDto();
        treatmentDto.setId(treatment.getId());
        treatmentDto.setPatientName(treatment.getPatient().getName()+" "+treatment.getPatient().getSurname());
        treatmentDto.setPatient(treatment.getPatient().getId());
        treatmentDto.setSpecialist(treatment.getSpecialist().getId());
        treatmentDto.setSpecialistName(treatment.getSpecialist().getName()+" "+treatment.getSpecialist().getSurname());
        treatmentDto.setEnterDate(treatment.getEnterDate());
        treatmentDto.setCheckOutDate(treatment.getCheckOutDate());
        treatmentDto.setRoomNumber(treatment.getRoom().getNumber());
        treatmentDto.setRoom(treatment.getRoom().getId());
        treatmentDto.setTemp(treatment.getTemp());
        treatmentDto.setState(treatment.getState());
        return treatmentDto;
    }
}
