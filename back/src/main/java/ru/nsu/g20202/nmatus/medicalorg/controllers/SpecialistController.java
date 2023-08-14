package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.SpecialistTypeDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.SpecialistType;
import ru.nsu.g20202.nmatus.medicalorg.services.SpecialistService;

import java.util.List;

@RestController
@CrossOrigin
public class SpecialistController {
    private final SpecialistService specialistService;

    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping("/specialists")
    List<SpecialistDto> getSpecialists() {
        return specialistService.getAllSpecialists();
    }

    @GetMapping("/hospital+specialists")
    List<SpecialistDto> getHospitalSpecialists() {
        return specialistService.getHospitalSpecialists();
    }

    @GetMapping("/clinic+specialists")
    List<SpecialistDto> getClinicSpecialists() {
        return specialistService.getClinicSpecialists();
    }

    @GetMapping("/specialists/organization")
    List<SpecialistDto> getSpecialistsByOrganization(@RequestParam Integer organization) {
        return specialistService.getSpecialistsByOrganization(organization);
    }

    @GetMapping("/specialists/type")
    List<SpecialistDto> getSpecialistsByType(@RequestParam String type, @RequestParam String organization, @RequestParam Integer experience) {
        if(experience == 0) return specialistService.getSpecialistsByType(type, organization);
        return specialistService.getSpecialistsByTypeAndExperience(type, organization, experience);
    }

    @GetMapping("/operating+specialists/type")
    List<SpecialistDto> getOperatingSpecialistsByType(@RequestParam String type, @RequestParam String organization, @RequestParam Integer operations) {
        return specialistService.getSpecialistsByTypeAndOperations(type, organization, operations);
    }

    @GetMapping("/specialists/degree")
    List<SpecialistDto> getSpecialistsWithDegreeByType(@RequestParam String type, @RequestParam String organization, @RequestParam String degree, @RequestParam String title) {
        return specialistService.getSpecialistsWithDegreeByType(type, organization, degree, title);
    }

    @GetMapping("/specialists+types")
    Iterable<SpecialistTypeDto> getTypes() {
        return specialistService.getTypes();
    }

    @GetMapping("/specialists/clinic+statistic")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<SpecialistStatisticDto> getSpecialistStatisticByClinic(@RequestParam Integer id, @RequestParam String start, @RequestParam String end) {
        return specialistService.getSpecialistStatisticByClinic(id, start, end);
    }

    @GetMapping("/specialists/type+statistic")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<SpecialistStatisticDto> getSpecialistStatisticByType(@RequestParam Integer id, @RequestParam String start, @RequestParam String end) {
        return specialistService.getSpecialistStatisticByType(id, start, end);
    }

    @GetMapping("/specialists/specialist+statistic")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<SpecialistStatisticDto> getSpecialistStatisticById(@RequestParam Integer id, @RequestParam String start, @RequestParam String end) {
        return specialistService.getSpecialistStatisticById(id, start, end);
    }

    @PostMapping("/specialists")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addSpecialist(@RequestBody SpecialistDto specialist) {
        specialistService.addSpecialist(specialist);
    }

    @PutMapping("/specialists")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateSpecialist(@RequestBody SpecialistDto specialist) {
        specialistService.updateSpecialist(specialist);
    }

    @DeleteMapping("/specialists")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteSpecialist(@RequestParam Integer id) {
        specialistService.deleteSpecialist(id);
    }

}
