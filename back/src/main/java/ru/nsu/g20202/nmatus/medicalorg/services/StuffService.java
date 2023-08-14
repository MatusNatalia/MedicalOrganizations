package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.persons.StuffDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Stuff;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.StuffType;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StuffService {

    private StuffRepository stuffRepository;
    private OrganizationRepository organizationRepository;
    private StuffTypeRepository stuffTypeRepository;

    public StuffService(StuffRepository stuffRepository, OrganizationRepository organizationRepository, StuffTypeRepository stuffTypeRepository) {
       this.stuffRepository = stuffRepository;
        this.organizationRepository = organizationRepository;
        this.stuffTypeRepository = stuffTypeRepository;
    }

    public List<StuffDto> getAllStuff(){
        List<StuffDto> stuffDtos = new ArrayList<>();
        for(Stuff stuff : stuffRepository.findAll()){
            stuffDtos.add(this.toDto(stuff));
        }
        return stuffDtos;
    }

    public List<StuffDto> getStuffByType(String type, String organization){
        List<StuffDto> stuffDtos = new ArrayList<>();
        if(Objects.equals(organization, "0")) {
            for (Stuff stuff : stuffRepository.findByType(stuffTypeRepository.findByType(type))) {
                stuffDtos.add(this.toDto(stuff));
            }
        }
        else{
            for(Stuff stuff : stuffRepository.getByTypeAndOrganization(stuffTypeRepository.findByType(type).getId(), organizationRepository.findByName(organization).getId())){
                stuffDtos.add(this.toDto(stuff));
            }
        }
        return stuffDtos;
    }

    public Iterable<StuffType> getTypes(){
        return stuffTypeRepository.findAll();
    }

    public void addStuff(StuffDto stuffDto) {
        try {
            stuffRepository.save(this.toEntity(stuffDto));
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

    public void updateStuff(StuffDto stuffDto) {
        try {
            stuffRepository.save(this.toEntity(stuffDto));
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

    public void deleteStuff(Integer id){
        stuffRepository.deleteById(id);
    }

    public StuffDto toDto(Stuff stuff){
        StuffDto dto = new StuffDto();
        dto.setId(stuff.getId());
        dto.setName(stuff.getName());
        dto.setSurname(stuff.getSurname());
        dto.setType(stuff.getType().getType());
        dto.setOrganization(stuff.getOrganization().getName());
        return dto;
    }

    public Stuff toEntity(StuffDto dto){
        Stuff entity = new Stuff();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        if(dto.getType() == null || dto.getOrganization() == null){
            List<String> errors = new ArrayList<>();
            errors.add("Заполните все поля");
            throw new IllegalValueException(errors);
        }
        entity.setType(stuffTypeRepository.findByType(dto.getType()));
        if(organizationRepository.findByName(dto.getOrganization()) == null){
            List<String> errors = new ArrayList<>();
            errors.add("Выберите организацию");
            throw new IllegalValueException(errors);
        }
        entity.setOrganization(organizationRepository.findByName(dto.getOrganization()));
        return entity;
    }

}
