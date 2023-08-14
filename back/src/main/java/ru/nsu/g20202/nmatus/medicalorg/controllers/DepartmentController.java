package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.DepartmentDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;
import ru.nsu.g20202.nmatus.medicalorg.services.DepartmentService;

import java.util.List;

@RestController
@CrossOrigin
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    List<DepartmentDto> getHospitalDepartments(@RequestParam Integer id){
        return departmentService.getHospitalDepartments(id);
    }

    @GetMapping("/block+departments")
    List<DepartmentDto> getBlockDepartments(@RequestParam Integer id){
        return departmentService.getBlockDepartments(id);
    }

    @GetMapping("/department+types")
    Iterable<DiseaseType> getDepartmentTypes(){
        return departmentService.getDepartmentTypes();
    }

    @PostMapping("/departments")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addDepartment(@RequestBody DepartmentDto departmentDto){
        departmentService.addDepartment(departmentDto);
    }

    @PutMapping("/departments")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentService.updateDepartment(departmentDto);
    }

    @DeleteMapping("/departments/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
    }
}
