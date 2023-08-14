package ru.nsu.g20202.nmatus.medicalorg.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Cabinet;

public interface CabinetRepository extends CrudRepository<Cabinet, Integer> {
    Cabinet findByNumber(String number);

    void deleteCabinetByNumber(String number);

    @Query(value = "SELECT COUNT(*) " +
            "FROM visit " +
            "WHERE cabinet_id=:id " +
            "AND time BETWEEN :start AND :end",
            nativeQuery = true)
    Integer getVisits(@Param("id") Integer id, @Param("start") String start, @Param("end") String end);

}

