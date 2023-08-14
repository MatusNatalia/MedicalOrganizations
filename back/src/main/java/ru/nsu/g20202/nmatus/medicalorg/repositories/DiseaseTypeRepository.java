package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.SpecialistType;

@Repository
public interface DiseaseTypeRepository extends CrudRepository<DiseaseType, Integer> {
    DiseaseType findByType(String type);
}

