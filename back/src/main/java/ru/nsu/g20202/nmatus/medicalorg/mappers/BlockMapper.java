package ru.nsu.g20202.nmatus.medicalorg.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.BlockDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Block;
import ru.nsu.g20202.nmatus.medicalorg.repositories.BlockRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.HospitalRepository;

@Component
public class BlockMapper {

    private static HospitalRepository hospitalRepository;
    private static BlockRepository blockRepository;

    @Autowired
    public BlockMapper(HospitalRepository hospitalRepository,
                       BlockRepository blockRepository){
        BlockMapper.hospitalRepository = hospitalRepository;
        BlockMapper.blockRepository = blockRepository;
    }

    public static BlockDto toDto(Block block){
        BlockDto blockDto = new BlockDto();
        blockDto.setId(block.getId());
        blockDto.setName(block.getName());
        blockDto.setAddress(block.getAddress());
        blockDto.setHospital(block.getHospital().getName());
        return blockDto;
    }

    public static Block toEntity(BlockDto blockDto){
        Block block;
        if(blockDto.getId() != null && blockRepository.existsById(blockDto.getId())){
            block =  blockRepository.findById(blockDto.getId()).get();
            block.setId(blockDto.getId());
        }
        else {
            block = new Block();
        }
        block.setName(blockDto.getName());
        block.setAddress(blockDto.getAddress());
        block.setHospital(hospitalRepository.findByName(blockDto.getHospital()).get(0));
        return block;
    }

}
