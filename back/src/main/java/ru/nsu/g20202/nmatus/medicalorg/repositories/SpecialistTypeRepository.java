package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.SpecialistType;

public interface SpecialistTypeRepository extends CrudRepository<SpecialistType, Integer> {
    SpecialistType findByType(String type);
}

