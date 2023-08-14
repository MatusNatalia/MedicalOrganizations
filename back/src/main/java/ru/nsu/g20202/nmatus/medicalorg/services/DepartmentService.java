package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.DepartmentDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Block;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Department;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Hospital;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.DepartmentMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.BlockRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.DepartmentRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.DiseaseTypeRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.HospitalRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {


    private final BlockRepository blockRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final DiseaseTypeRepository diseaseTypeRepository;

    public DepartmentService(BlockRepository blockRepository,
                             HospitalRepository hospitalRepository,
                             DepartmentRepository departmentRepository,
                             DiseaseTypeRepository diseaseTypeRepository) {
        this.blockRepository = blockRepository;
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
        this.diseaseTypeRepository = diseaseTypeRepository;
    }

    public List<DepartmentDto> getHospitalDepartments(Integer id){
        List<DepartmentDto> dtos = new ArrayList<>();
        Hospital hospital = hospitalRepository.findById(id).get();
        for(Block block : hospital.getBlocks()){
            for(Department department : block.getDepartments()){
                dtos.add(DepartmentMapper.toDto(department));
            }
        }
        return dtos;
    }

    public List<DepartmentDto> getBlockDepartments(Integer id){
        List<DepartmentDto> dtos = new ArrayList<>();
        Block block = blockRepository.findById(id).get();
        for(Department department : block.getDepartments()){
            dtos.add(DepartmentMapper.toDto(department));
        }
        return dtos;
    }

    public Iterable<DiseaseType> getDepartmentTypes(){
        return diseaseTypeRepository.findAll();
    }

    @Transactional
    public void addDepartment(DepartmentDto dto){
        departmentRepository.save(DepartmentMapper.toEntity(dto));
    }

    @Transactional
    public void updateDepartment(DepartmentDto dto) {
        try {
            departmentRepository.save(DepartmentMapper.toEntity(dto));
        } catch(TransactionSystemException e){
            if (e.getRootCause() instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException) e.getRootCause();
                List<String> errors = new ArrayList<>();
                for(ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()){
                    errors.add(constraintViolation.getMessage());
                }
                throw new IllegalValueException(errors);
            }
        }
    }

    public void deleteDepartment(Integer id){
        departmentRepository.deleteById(id);
    }

}
