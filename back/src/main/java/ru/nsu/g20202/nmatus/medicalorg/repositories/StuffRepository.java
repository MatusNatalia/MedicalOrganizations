package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Stuff;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.StuffType;

import java.util.List;

public interface StuffRepository extends CrudRepository<Stuff, Integer> {
    List<Stuff> findByType(StuffType type);

    @Query(value = "SELECT stuff.*, person.name, person.surname FROM stuff "+
            "JOIN person ON person.id=stuff.id "+
            "WHERE stuff.type_id=:type AND stuff.organization_id=:organization",
            nativeQuery = true)
    List<Stuff> getByTypeAndOrganization(@Param("type") Integer type, @Param("organization") Integer organization);

}

