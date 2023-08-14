package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.DepartmentDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.RoomDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Block;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Department;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Room;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.DepartmentMapper;
import ru.nsu.g20202.nmatus.medicalorg.mappers.RoomMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;

    public RoomService(DepartmentRepository departmentRepository,
                             RoomRepository roomRepository) {
        this.departmentRepository = departmentRepository;
        this.roomRepository = roomRepository;
    }

    public List<RoomDto> getDepartmentRooms(Integer id){
        List<RoomDto> dtos = new ArrayList<>();
        Department department = departmentRepository.findById(id).get();
        for(Room room : department.getRooms()){
            dtos.add(RoomMapper.toDto(room));
        }
        return dtos;
    }


    @Transactional
    public void addRoom(RoomDto dto){
        roomRepository.save(RoomMapper.toEntity(dto));
    }

    @Transactional
    public void updateRoom(RoomDto dto) {
        try {
            roomRepository.save(RoomMapper.toEntity(dto));
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

    public void deleteRoom(Integer id){
        roomRepository.deleteById(id);
    }

}
