package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.StuffDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.StuffType;
import ru.nsu.g20202.nmatus.medicalorg.services.StuffService;

import java.util.List;

@RestController
@CrossOrigin
public class StuffController {
    private final StuffService stuffService;

    public StuffController(StuffService stuffService) {
        this.stuffService = stuffService;
    }

    @GetMapping("/stuff")
    List<StuffDto> getStuff() {
        return stuffService.getAllStuff();
    }

    @GetMapping("/stuff/type")
    List<StuffDto> getStuffByType(@RequestParam String type, @RequestParam String organization) {
        return stuffService.getStuffByType(type, organization);
    }

    @GetMapping("/stuff+types")
    Iterable<StuffType> getTypes() {
        return stuffService.getTypes();
    }

    @PostMapping("/stuff")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addStuff(@RequestBody StuffDto stuff) {
        stuffService.addStuff(stuff);
    }

    @PutMapping("/stuff")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateStuff(@RequestBody StuffDto stuff) {
        stuffService.updateStuff(stuff);
    }

    @DeleteMapping("/stuff")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteStuff(@RequestParam Integer id) {
        stuffService.deleteStuff(id);
    }

}
