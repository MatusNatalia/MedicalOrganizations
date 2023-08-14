package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.CabinetDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.OperationDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.StudyDto;
import ru.nsu.g20202.nmatus.medicalorg.services.OperationService;
import ru.nsu.g20202.nmatus.medicalorg.services.StudyService;

import java.util.List;

@RestController
@CrossOrigin
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/operation")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    List<OperationDto> getOperations(@RequestParam Integer hospital, @RequestParam Integer specialist,
                                     @RequestParam String start, @RequestParam String end){
        return operationService.getOperations(hospital, specialist, start, end);
    }

    @PostMapping("/operation")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    void addOperation(@RequestBody OperationDto operationDto) {
        operationService.addOperation(operationDto);
    }

}
