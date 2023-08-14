package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.RoomDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Room;
import ru.nsu.g20202.nmatus.medicalorg.repositories.DepartmentRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.RoomRepository;

@Component
public class RoomMapper {
    private static DepartmentRepository departmentRepository;
    private static RoomRepository roomRepository;

    @Autowired
    public RoomMapper(DepartmentRepository departmentRepository,
                            RoomRepository roomRepository){
        RoomMapper.departmentRepository = departmentRepository;
        RoomMapper.roomRepository = roomRepository;
    }

    public static RoomDto toDto(Room room){
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setNumber(room.getNumber());
        dto.setSize(room.getSize());
        dto.setBusy(room.getBusy());
        dto.setDepartment(room.getDepartment().getId());
        return dto;
    }

    public static Room toEntity(RoomDto roomDto){
        Room room;
        if(roomDto.getId() != null && roomRepository.existsById(roomDto.getId())){
            room =  roomRepository.findById(roomDto.getId()).get();
        }
        else {
            room = new Room();
            room.setFree(true);
        }
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        room.setBusy(roomDto.getBusy());
        room.setDepartment(departmentRepository.findById(roomDto.getDepartment()).get());
        return room;
    }
}
