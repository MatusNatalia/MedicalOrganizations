package ru.nsu.g20202.nmatus.medicalorg.loaders;

import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Hospital;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Laboratory;
import ru.nsu.g20202.nmatus.medicalorg.repositories.ClinicRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.HospitalRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.LaboratoryRepository;

import java.util.List;

@Component
public class DataLoader {
    private final HospitalRepository hospitalRepository;
    private final ClinicRepository clinicRepository;
    private final LaboratoryRepository laboratoryRepository;

    public DataLoader(HospitalRepository hospitalRepository, ClinicRepository clinicRepository, LaboratoryRepository laboratoryRepository){
        this.hospitalRepository = hospitalRepository;
        this.clinicRepository = clinicRepository;
        this.laboratoryRepository = laboratoryRepository;

    }

    public void loadHospitals(){
        hospitalRepository.saveAll(List.of(
                new Hospital("Больница №1", "3321145"),
                new Hospital("Больница №2", "3326578"),
                new Hospital("Инфекционная больница №1", "3329823"),
                new Hospital("Областная Больница", "3321672")
        ));
    }

    /*public void loadClinics(){
        clinicRepository.saveAll(List.of(
                new Clinic("Городская поликлиника №1", "3324392", "Ул. Восточная, 10",
                        hospitalRepository.findByName("Больница №1").get(0)),
                new Clinic("Детская поликлиника №1", "3328954", "Ул. Западная, 11"),
                new Clinic("Городская поликлиника №2", "3327536", "Ул. Южная, 12",
                        hospitalRepository.findByName("Больница №2").get(0))
        ));
    }*/
}
