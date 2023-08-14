package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.SpecialistType;

import java.util.List;

public interface SpecialistRepository extends CrudRepository<Specialist, Integer> {
    List<Specialist> findByType(SpecialistType type);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM specialist "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.organization_id=:organization",
            nativeQuery = true)
    List<Specialist> getSpecialistsByOrganization(@Param("organization") Integer organization);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM specialist "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type AND specialist.organization_id=:organization",
            nativeQuery = true)
    List<Specialist> getByTypeAndOrganization(@Param("type") Integer type, @Param("organization") Integer organization);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM specialist "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type AND specialist.experience>=:experience",
            nativeQuery = true)
    List<Specialist> getByTypeAndExperience(@Param("type") Integer typeId, @Param("experience") Integer experience);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM specialist "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type AND specialist.organization_id=:organization AND specialist.experience>=:experience",
            nativeQuery = true)
    List<Specialist> getByTypeAndExperienceAndOrganization(@Param("type") Integer typeId, @Param("organization") Integer organization, @Param("experience") Integer experience);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM operation "+
            "JOIN specialist ON operation.specialist_id=specialist.id "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type AND specialist.organization_id=:organization "+
            "GROUP BY operation.specialist_id "+
            "HAVING COUNT(operation.specilist_id) >= :operations",
            nativeQuery = true)
    List<Specialist> getByTypeAndOperationsAndOrganization(@Param("type") Integer typeId, @Param("organization") Integer organizationId, @Param("operations") Integer operations);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM operation "+
            "JOIN specialist ON operation.specialist_id=specialist.id "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type "+
            "GROUP BY operation.specialist_id "+
            "HAVING COUNT(specialist.id) >= :operations",
            nativeQuery = true)
    List<Specialist> getByTypeAndOperations(@Param("type") Integer typeId, @Param("operations") Integer operations);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM specialist "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type AND specialist.degree=:degree AND specialist.title=:title",
            nativeQuery = true)
    List<Specialist> getByTypeAndDegreeAndTitle(@Param("type") Integer typeId, @Param("degree") String degree,  @Param("title") String title);

    @Query(value = "SELECT specialist.*, person.name, person.surname FROM specialist "+
            "JOIN person ON person.id=specialist.id "+
            "WHERE specialist.type_id=:type AND specialist.organization_id=:organization " +
            "AND specialist.degree=:degree AND specialist.title=:title",
            nativeQuery = true)
    List<Specialist> getByTypeAndDegreeAndTitleAndOrganization(@Param("type") Integer typeId, @Param("organization") Integer organization, @Param("degree") String degree,  @Param("title") String title);


    @Query(value = "SELECT AVG(count) " +
            "FROM " +
            "(SELECT COUNT(*) AS count FROM visit " +
            "WHERE specialist_id=:id " +
            "AND time BETWEEN :start AND :end GROUP BY time) subtable",
            nativeQuery = true)
    Double getAvgPatients(@Param("id") Integer id, @Param("start") String start, @Param("end") String end);

}

