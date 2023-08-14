package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.BlockDto;
import ru.nsu.g20202.nmatus.medicalorg.services.BlockService;

import java.util.List;

@RestController
@CrossOrigin
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping("/blocks")
    List<BlockDto> getHospitalBlocks(@RequestParam Integer id){
        return blockService.getHospitalBlocks(id);
    }

    @PostMapping("/blocks")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addBlock(@RequestBody BlockDto blockDto){
        blockService.addBlock(blockDto);
    }

    @PutMapping("/blocks")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateBlock(@RequestBody BlockDto blockDto) {
        blockService.updateBlock(blockDto);
    }

    @DeleteMapping("/blocks/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteBlock(@PathVariable Integer id) {
        blockService.deleteBlock(id);
    }

}
