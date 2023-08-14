package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;

import java.util.List;

public interface ClinicRepository extends CrudRepository<Clinic, Integer> {
    List<Clinic> findByName(String name);
    boolean existsByName(String name);

    @Query(value = "SELECT COUNT(*) FROM Cabinet WHERE clinic_id = :id",
            nativeQuery = true)
    Integer getNumberOfRooms(@Param("id") Integer id);
}

