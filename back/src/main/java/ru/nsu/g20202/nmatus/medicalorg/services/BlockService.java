package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.BlockDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.HospitalDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Block;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.mappers.BlockMapper;
import ru.nsu.g20202.nmatus.medicalorg.repositories.BlockRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.HospitalRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockService {

    private final BlockRepository blockRepository;
    private final HospitalRepository hospitalRepository;

    public BlockService(BlockRepository blockRepository, HospitalRepository hospitalRepository) {
        this.blockRepository = blockRepository;
        this.hospitalRepository = hospitalRepository;
    }

    public List<BlockDto> getHospitalBlocks(Integer id){
        List<BlockDto> dtos = new ArrayList<>();
        for(Block block : hospitalRepository.findById(id).get().getBlocks()){
            dtos.add(BlockMapper.toDto(block));
        }
        return dtos;
    }

    public void addBlock(BlockDto blockDto){
        try {
            blockRepository.save(BlockMapper.toEntity(blockDto));
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

    @Transactional
    public void updateBlock(BlockDto blockDto) {
        try {
            blockRepository.save(BlockMapper.toEntity(blockDto));
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

    public void deleteBlock(Integer id){
       blockRepository.deleteById(id);
    }

}
