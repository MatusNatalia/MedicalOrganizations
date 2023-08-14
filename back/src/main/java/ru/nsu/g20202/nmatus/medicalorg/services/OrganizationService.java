package ru.nsu.g20202.nmatus.medicalorg.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.CabinetDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.CabinetStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.ClinicDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.clinic.ClinicStatisticDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.hospital.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Cabinet;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Block;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Department;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Hospital;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Room;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Laboratory;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;
import ru.nsu.g20202.nmatus.medicalorg.exceptions.IllegalValueException;
import ru.nsu.g20202.nmatus.medicalorg.repositories.*;

import java.util.*;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SpecialistRepository specialistRepository;
    private final HospitalRepository hospitalRepository;
    private final ClinicRepository clinicRepository;
    private final CabinetRepository cabinetRepository;
    private final LaboratoryService laboratoryService;

    public OrganizationService(OrganizationRepository organizationRepository,
                               HospitalRepository hospitalRepository,
                               ClinicRepository clinicRepository,
                               CabinetRepository cabinetRepository,
                               LaboratoryService laboratoryService,
                               SpecialistRepository specialistRepository) {
        this.organizationRepository = organizationRepository;
        this.hospitalRepository = hospitalRepository;
        this.clinicRepository = clinicRepository;
        this.cabinetRepository = cabinetRepository;
        this.laboratoryService = laboratoryService;
        this.specialistRepository = specialistRepository;
    }

    public List<HospitalDto> getAllHospitals(){
        List<HospitalDto> hospitalDtoList = new ArrayList<>();
        for(Hospital hospital : hospitalRepository.findAll()){
              hospitalDtoList.add(this.hospitalToDto(hospital));
        }
        return hospitalDtoList;
    }

    public List<ClinicDto> getAllClinics(){
        List<ClinicDto> clinicDtoList = new ArrayList<>();
        for(Clinic clinic : clinicRepository.findAll()){
            clinicDtoList.add(this.clinicToDto(clinic));
        }
        return clinicDtoList;
    }

    public ResponseEntity<?> getOrganizationInfo(Integer id, String start, String end){
        if(clinicRepository.existsById(id)){
            return this.getClinicStatistic(id, start, end);
        }
        return this.getHospitalStatistic(id);
    }

    public ResponseEntity<?> getClinicStatistic(Integer id, String start, String end){
        if(!start.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        if(!end.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            List<String> msg = new ArrayList<>();
            msg.add("Введите дату в формате ГГГГ-ММ-ДД");
            throw new IllegalValueException(msg);
        }
        ClinicStatisticDto statisticDto = new ClinicStatisticDto();
        statisticDto.setCabinetNumber(clinicRepository.getNumberOfRooms(id));
        Clinic clinic = clinicRepository.findById(id).get();
        List<CabinetStatisticDto> cabinetStatisticDtos = new ArrayList<>();
        for(Cabinet cabinet : clinic.getCabinets()){
            CabinetStatisticDto dto = new CabinetStatisticDto();
            dto.setNumber(cabinet.getNumber());
            dto.setVisits(cabinetRepository.getVisits(cabinet.getId(), start, end));
            cabinetStatisticDtos.add(dto);
        }
        statisticDto.setCabinetStatistic(cabinetStatisticDtos);
        return new ResponseEntity<>(statisticDto, HttpStatus.OK);
    }

    public ResponseEntity<?> getHospitalStatistic(Integer id){
        HospitalStatisticDto statisticDto = new HospitalStatisticDto();
        statisticDto.setTotalRoomNumber(hospitalRepository.getNumberOfRooms(id));
        statisticDto.setTotalBedNumber(hospitalRepository.getNumberOfBeds(id));
        Hospital hospital = hospitalRepository.findById(id).get();
        List<DepartmentStatisticDto> departmentStatisticDtos = new ArrayList<>();
        for(Block block : hospital.getBlocks()){
            for(Department department : block.getDepartments()){
                DepartmentStatisticDto departmentStatisticDto = new DepartmentStatisticDto();
                departmentStatisticDto.setName(department.getName());
                departmentStatisticDto.setFreeRoomNumber(hospitalRepository.getNumberOfFreeRooms(department.getId()));
                Integer freeBeds = hospitalRepository.getNumberOfFreeBeds(department.getId());
                departmentStatisticDto.setFreeBedNumber(freeBeds == null ? 0 : freeBeds);
                departmentStatisticDtos.add(departmentStatisticDto);
            }
        }
        statisticDto.setDepartmentStatistic(departmentStatisticDtos);
        return new ResponseEntity<>(statisticDto, HttpStatus.OK);
    }

    public List<LaboratoryDto> getOrganizationLaboratories(Integer id){
        List<LaboratoryDto> dtos = new ArrayList<>();
        for(Laboratory laboratory : organizationRepository.findById(id).get().getLaboratories()){
            dtos.add(laboratoryService.toDto(laboratory));
        }
        return dtos;
    }

    public List<CabinetDto> getClinicCabinets(Integer id){
        List<CabinetDto> dtos = new ArrayList<>();
        Clinic clinic = clinicRepository.findById(id).get();
        for(Cabinet cabinet : clinic.getCabinets()){
            dtos.add(this.cabinetToDto(cabinet));
        }
        return dtos;
    }

    public void updateOrganizationLaboratories(Integer id, List<LaboratoryDto> labs){
        List<Laboratory> laboratories = new ArrayList<>();
        for(LaboratoryDto laboratoryDto : labs){
            laboratories.add(laboratoryService.toEntity(laboratoryDto));
        }
        Organization organization = organizationRepository.findById(id).get();
        organization.setLaboratories(laboratories);
        System.out.println(organization.getId());
        organization.setId(organization.getId());
        organizationRepository.save(organization);
    }

    public HospitalDto hospitalToDto(Hospital organization){
        HospitalDto dto = new HospitalDto();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setNumber(organization.getNumber());
        List<String> addresses = new ArrayList<>();
        List<String> blocks = new ArrayList<>();
        List<String> departments = new ArrayList<>();
        for(Block block : organization.getBlocks()){
            addresses.add(block.getAddress());
            blocks.add(block.getName());
            for(Department department : block.getDepartments()){
                departments.add(department.getName());
            }
        }
        dto.setAddresses(addresses);
        dto.setBlocks(blocks);
        dto.setDepartments(departments);
        return dto;
    }

    public void updateClinic(ClinicDto clinicDto) {
        try {
            clinicRepository.save(this.clinicToEntity(clinicDto));
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
    public void updateHospital(HospitalDto hospitalDto) {
        try {
            hospitalRepository.save(this.hospitalToEntity(hospitalDto));
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

    public void createClinic(ClinicDto clinicDto){
        try {
            clinicRepository.save(this.clinicToEntity(clinicDto));
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

    public void createHospital(HospitalDto hospitalDto){
        try {
            hospitalRepository.save(this.hospitalToEntity(hospitalDto));
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

    public void addCabinet(CabinetDto cabinetDto){
        List<String> errors = new ArrayList<>();
        if(cabinetRepository.findByNumber(cabinetDto.getNumber()) != null &&
                Objects.equals(cabinetRepository.findByNumber(cabinetDto.getNumber()).getClinic().getName(), cabinetDto.getClinic())){
            errors.add("Кабинет уже существует");
            throw new IllegalValueException(errors);
        }
        Cabinet cabinet = new Cabinet();
        cabinet.setNumber(cabinetDto.getNumber());
        cabinet.setClinic(clinicRepository.findByName(cabinetDto.getClinic()).get(0));
        cabinet.setSpecialists(new ArrayList<>());
        cabinetRepository.save(cabinet);
    }

    public void deleteOrganization(Integer id){
        if(clinicRepository.existsById(id)){
            clinicRepository.deleteById(id);
        }
        else{
            hospitalRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteCabinet(Integer number){
        cabinetRepository.deleteCabinetByNumber(Integer.toString(number));
    }


    public ClinicDto clinicToDto(Clinic organization){
        ClinicDto dto = new ClinicDto();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setNumber(organization.getNumber());
        dto.setAddress(organization.getAddress());
        if(organization.getHospital() != null){
            dto.setHospital(this.hospitalToDto(organization.getHospital()));
        }
        return dto;
    }

    public Clinic clinicToEntity(ClinicDto clinicDto){
        Clinic clinic;
        if(clinicDto.getId() != null && clinicRepository.existsById(clinicDto.getId())){
            clinic = clinicRepository.findById(clinicDto.getId()).get();
            clinic.setId(clinicDto.getId());
        }
        else {
            clinic = new Clinic();
        }
        clinic.setName(clinicDto.getName());
        clinic.setAddress(clinicDto.getAddress());
        clinic.setNumber(clinicDto.getNumber());
        if(clinicDto.getHospital() != null){
            clinic.setHospital(hospitalRepository.findById(clinicDto.getHospital().getId()).get());
        }
        return clinic;
    }

    public Hospital hospitalToEntity(HospitalDto hospitalDto){
        Hospital hospital;
        if(hospitalDto.getId() != null && hospitalRepository.existsById(hospitalDto.getId())){
            hospital = hospitalRepository.findById(hospitalDto.getId()).get();
            hospital.setId(hospitalDto.getId());
        }
        else {
            hospital = new Hospital();
        }
        hospital.setName(hospitalDto.getName());
        hospital.setNumber(hospitalDto.getNumber());
        return hospital;
    }

    public CabinetDto cabinetToDto(Cabinet cabinet){
        CabinetDto dto = new CabinetDto();
        dto.setNumber(cabinet.getNumber());
        List<String> specialists = new ArrayList<>();
        for(Specialist specialist : cabinet.getSpecialists()){
            specialists.add(specialist.getName() + " " + specialist.getSurname() + ", " + specialist.getType().getType());
        }
        dto.setSpecialists(specialists);
        return dto;
    }


}
