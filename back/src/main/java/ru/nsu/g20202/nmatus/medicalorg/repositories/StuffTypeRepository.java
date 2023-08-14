package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.StuffType;

public interface StuffTypeRepository extends CrudRepository<StuffType, Integer> {
    StuffType findByType(String type);
}

