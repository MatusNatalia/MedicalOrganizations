package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.LaboratoryType;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.StuffType;

public interface LaboratoryTypeRepository extends CrudRepository<LaboratoryType, Integer> {
    LaboratoryType findByType(String type);
}

