package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Visit;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Operation;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Treatment;

import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Integer> {

    @Query(value = "SELECT operation.* FROM operation "+
            "JOIN specialist ON specialist.id=operation.specialist_id "+
            "WHERE specialist.organization_id=:hospital "+
            "AND operation.date BETWEEN :start AND :end",
            nativeQuery = true)
    List<Operation> getOperationsByHospital(@Param("hospital") Integer hospital,
                                            @Param("start") String start,
                                            @Param("end") String end);

    @Query(value = "SELECT operation.* FROM operation "+
            "WHERE operation.specialist_id=:specialist "+
            "AND operation.date BETWEEN :start AND :end",
            nativeQuery = true)
    List<Operation> getOperationsBySpecialist(@Param("specialist") Integer specialist,
                                            @Param("start") String start,
                                            @Param("end") String end);
}
