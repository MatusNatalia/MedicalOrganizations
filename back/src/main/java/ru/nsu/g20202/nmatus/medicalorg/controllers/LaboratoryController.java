package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.ClinicDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.HospitalDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.LaboratoryType;
import ru.nsu.g20202.nmatus.medicalorg.services.LaboratoryService;
import java.util.List;

@RestController
@CrossOrigin
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping("/laboratories")
    List<LaboratoryDto> getLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    @GetMapping("/laboratory")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<LaboratoryStatisticDto> getLaboratoryStatistic(@RequestParam String organization, @RequestParam String start, @RequestParam String end) {
        return laboratoryService.getStatistic(organization, start, end);
    }

    @GetMapping("/laboratory+types")
    Iterable<LaboratoryType> getLaboratoryTypes(){
        return laboratoryService.getLaboratoryTypes();
    }

    @PostMapping("/laboratory")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void createLaboratory(@RequestBody LaboratoryDto laboratoryDto) {
        laboratoryService.addLaboratory(laboratoryDto);
    }

    @PutMapping("/laboratory")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateLaboratory(@RequestBody LaboratoryDto laboratoryDto) {
        laboratoryService.updateLaboratory(laboratoryDto);
    }

    @DeleteMapping("/laboratory/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteLaboratory(@PathVariable Integer id) {
        laboratoryService.deleteLaboratory(id);
    }

}