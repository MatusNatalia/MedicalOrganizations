package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.CabinetDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.ClinicDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.BlockDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.DepartmentDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.HospitalDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.RoomDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.PatientDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Hospital;
import ru.nsu.g20202.nmatus.medicalorg.services.OrganizationService;
import java.util.List;
@RestController
@CrossOrigin
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/hospitals")
    List<HospitalDto> getHospitals() {
        return organizationService.getAllHospitals();
    }

    @GetMapping("/clinics")
    List<ClinicDto> getClinics() {
        return organizationService.getAllClinics();
    }

    @GetMapping("/organization")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<?> getOrganizationInfo(@RequestParam Integer id, @RequestParam String start, @RequestParam String end){
        return organizationService.getOrganizationInfo(id, start, end);
    }

    @GetMapping("/organization+labs")
    List<LaboratoryDto> getOrganizationLaboratories(@RequestParam Integer id){
        return organizationService.getOrganizationLaboratories(id);
    }

    @GetMapping("/cabinets")
    List<CabinetDto> getClinicCabinets(@RequestParam Integer id){
        return organizationService.getClinicCabinets(id);
    }

    @PostMapping("/clinic")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void createClinic(@RequestBody ClinicDto clinic) {
        organizationService.createClinic(clinic);
    }

    @PostMapping("/hospital")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void createHospital(@RequestBody HospitalDto hospitalDto) {
        organizationService.createHospital(hospitalDto);
    }

    @PutMapping("/clinic")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateClinic(@RequestBody ClinicDto clinic) {
        organizationService.updateClinic(clinic);
    }

    @PutMapping("/hospital")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateHospital(@RequestBody HospitalDto hospitalDto) {
        organizationService.updateHospital(hospitalDto);
    }

    @PostMapping("/clinic/add+cabinet")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addCabinet(@RequestBody CabinetDto cabinet){
        organizationService.addCabinet(cabinet);
    }

    @PostMapping("/organization+labs")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateOrganizationLaboratories(@RequestParam Integer id, @RequestBody List<LaboratoryDto> labs){
        organizationService.updateOrganizationLaboratories(id, labs);
    }

    @DeleteMapping("organization/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteOrganization(@PathVariable Integer id) {
        organizationService.deleteOrganization(id);
    }

    @DeleteMapping("cabinets/{number}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCabinet(@PathVariable Integer number) {
        organizationService.deleteCabinet(number);
    }
}
