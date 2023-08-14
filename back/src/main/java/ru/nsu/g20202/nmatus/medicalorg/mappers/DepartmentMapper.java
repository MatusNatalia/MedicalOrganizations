package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.DepartmentDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Department;
import ru.nsu.g20202.nmatus.medicalorg.repositories.BlockRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.DepartmentRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.DiseaseTypeRepository;

@Component
public class DepartmentMapper {

    private static DepartmentRepository departmentRepository;
    private static BlockRepository blockRepository;
    private static DiseaseTypeRepository diseaseTypeRepository;

    @Autowired
    public DepartmentMapper(DepartmentRepository departmentRepository,
                       BlockRepository blockRepository,
                            DiseaseTypeRepository diseaseTypeRepository){
        DepartmentMapper.departmentRepository = departmentRepository;
        DepartmentMapper.blockRepository = blockRepository;
        DepartmentMapper.diseaseTypeRepository = diseaseTypeRepository;
    }

    public static DepartmentDto toDto(Department department){
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setBlock(department.getBlock().getId());
        dto.setType(department.getType().getType());
        return dto;
    }

    public static Department toEntity(DepartmentDto departmentDto){
        Department department;
        if(departmentDto.getId() != null && departmentRepository.existsById(departmentDto.getId())){
            department =  departmentRepository.findById(departmentDto.getId()).get();
        }
        else {
            department = new Department();
        }
        department.setName(departmentDto.getName());
        department.setType(diseaseTypeRepository.findByType(departmentDto.getType()));
        department.setBlock(blockRepository.findById(departmentDto.getBlock()).get());
        return department;
    }
}
