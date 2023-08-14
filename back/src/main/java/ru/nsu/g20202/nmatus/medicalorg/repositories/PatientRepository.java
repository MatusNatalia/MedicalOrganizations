package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Patient;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Integer> {

    @Query(value = "SELECT patient.*, person.name, person.surname FROM patient "+
            "JOIN person ON person.id=patient.id "+
            "WHERE patient.clinic_id=:id",
            nativeQuery = true)
    List<Patient> findByClinic(@Param("id") Integer id);

    @Query(value = "SELECT patient.*, person.name, person.surname FROM visit " +
            "JOIN person ON person.id=visit.patient_id " +
            "JOIN patient ON visit.patient_id=patient.id " +
            "JOIN specialist ON specialist.id=visit.specialist_id " +
            "WHERE specialist.type_id=:type "+
            "GROUP BY person.id",
            nativeQuery = true)
    List<Patient> findBySpecialistType(@Param("type") Integer type);
    @Query(value = "SELECT patient.*, person.name, person.surname FROM visit " +
            "JOIN person ON person.id=visit.patient_id " +
            "JOIN patient ON visit.patient_id=patient.id " +
            "JOIN specialist ON specialist.id=visit.specialist_id " +
            "WHERE specialist.type_id=:type AND patient.clinic_id=:clinic "+
            "GROUP BY person.id",
            nativeQuery = true)
    List<Patient> findBySpecialistTypeAndOrganization(@Param("type") Integer type, @Param("clinic") Integer clinic);

}

