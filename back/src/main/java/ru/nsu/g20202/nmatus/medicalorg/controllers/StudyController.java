package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.StudyDto;
import ru.nsu.g20202.nmatus.medicalorg.services.StudyService;

@RestController
@CrossOrigin
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @PostMapping("/study")
    @PostAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    void addStudy(@RequestBody StudyDto studyDto) {
        studyService.addStudy(studyDto);
    }

}
