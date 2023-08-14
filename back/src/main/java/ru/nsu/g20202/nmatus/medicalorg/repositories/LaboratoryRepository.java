package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Laboratory;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Integer> {
}