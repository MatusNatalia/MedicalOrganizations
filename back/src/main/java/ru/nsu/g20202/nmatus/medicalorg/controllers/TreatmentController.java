package ru.nsu.g20202.nmatus.medicalorg.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.TreatmentDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistDto;
import ru.nsu.g20202.nmatus.medicalorg.services.TreatmentService;

import java.util.List;

@RestController
@CrossOrigin
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping("/treatment")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<TreatmentDto> getTreatments(@RequestParam Integer organization, @RequestParam Integer department,
                                     @RequestParam Integer room) {
        if(department == 0){
            return treatmentService.getTreatmentsByOrganization(organization);
        }
        else if(room == 0){
            return treatmentService.getTreatmentsByDepartment(department);
        }
        else{
            return treatmentService.getTreatmentsByRoom(room);
        }
    }

    @GetMapping("/old+treatment")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<TreatmentDto> getOldTreatments(@RequestParam Integer organization, @RequestParam Integer specialist,
                                        @RequestParam String start, @RequestParam String end) {
        if(specialist == 0){
            return treatmentService.getOldTreatmentsByOrganization(organization, start, end);
        }
        else{
            return treatmentService.getOldTreatmentsBySpecialist(specialist, start, end);
        }
    }

    @GetMapping("/treatment/hospital")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<TreatmentDto> getTreatmentsByHospital(@RequestParam Integer id) {
        return treatmentService.getTreatmentsByOrganization(id);
    }

    @GetMapping("/treatment/type")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<TreatmentDto> getTreatmentsBySpecialistType(@RequestParam Integer id) {
        return treatmentService.getTreatmentsBySpecialistType(id);
    }

    @GetMapping("/treatment/specialist")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<TreatmentDto> getTreatmentsBySpecialist(@RequestParam Integer id) {
        return treatmentService.getTreatmentsBySpecialist(id);
    }

    @PostMapping("/treatment")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    void addTreatment(@RequestBody TreatmentDto treatmentDto) {
        treatmentService.addTreatment(treatmentDto);
    }

    @PutMapping("/treatment")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    void updateTreatment(@RequestBody TreatmentDto treatmentDto) {
        treatmentService.updateTreatment(treatmentDto);
    }

}
