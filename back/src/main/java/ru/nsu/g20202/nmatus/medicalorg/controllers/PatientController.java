package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.PatientDto;
import ru.nsu.g20202.nmatus.medicalorg.services.PatientService;

import java.util.List;

@RestController
@CrossOrigin
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<PatientDto> getPatient() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patient+organization")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<PatientDto> getPatientByClinic(@RequestParam String organization){
        return patientService.getPatientsByClinic(organization);
    }

    @GetMapping("/patient/type")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<PatientDto> getPatientsBySpecialistType(@RequestParam String type, @RequestParam String organization){
        return patientService.getPatientsBySpecialistType(type, organization);
    }

    @PostMapping("/patient")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addPatient(@RequestBody PatientDto patient) {
        patientService.addPatient(patient);
    }

    @PutMapping("/patient")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updatePatient(@RequestBody PatientDto patient) {
        patientService.updatePatient(patient);
    }

    @DeleteMapping("/patient")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deletePatient(@RequestParam Integer id) {
        patientService.deletePatient(id);
    }

}
