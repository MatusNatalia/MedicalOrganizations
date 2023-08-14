package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Visit;

public interface VisitRepository extends CrudRepository<Visit, Integer> {
}
