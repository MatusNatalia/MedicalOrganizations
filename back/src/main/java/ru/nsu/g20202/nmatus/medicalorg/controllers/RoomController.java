package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.DepartmentDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.RoomDto;
import ru.nsu.g20202.nmatus.medicalorg.services.RoomService;

import java.util.List;

@RestController
@CrossOrigin
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    List<RoomDto> getDepartmentRooms(@RequestParam Integer id){
        return roomService.getDepartmentRooms(id);
    }

    @PostMapping("/rooms")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addRoom(@RequestBody RoomDto roomDto){
        roomService.addRoom(roomDto);
    }

    @PutMapping("/rooms")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateRoom(@RequestBody RoomDto roomDto) {
        roomService.updateRoom(roomDto);
    }

    @DeleteMapping("/rooms/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
    }

}
