package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Study;

public interface StudyRepository extends CrudRepository<Study, Integer> {
    @Query(value = "SELECT AVG(count) " +
            "FROM " +
            "(SELECT COUNT(*) AS count FROM study " +
            "WHERE laboratory_id=:id " +
            "AND time BETWEEN :start AND :end GROUP BY time) subtable",
            nativeQuery = true)
    Integer getAvgStudies(@Param("id") Integer id, @Param("start") String start, @Param("end") String end);
}
