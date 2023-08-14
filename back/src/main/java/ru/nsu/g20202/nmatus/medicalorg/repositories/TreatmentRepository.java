package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Treatment;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;

import java.util.List;

@Repository
public interface TreatmentRepository extends CrudRepository<Treatment, Integer> {
    @Query(value = "SELECT treatment.* FROM treatment "+
            "JOIN specialist ON specialist.id=treatment.specialist_id "+
            "WHERE specialist.organization_id=:organization AND treatment.check_out_date IS NULL",
            nativeQuery = true)
    List<Treatment> getTreatmentByOrganization(@Param("organization") Integer organization);

    @Query(value = "SELECT treatment.* FROM treatment "+
            "JOIN room ON room.id=treatment.room_id "+
            "JOIN department ON department.id=room.department_id " +
            "WHERE department.id=:department AND treatment.check_out_date IS NULL",
            nativeQuery = true)
    List<Treatment> getTreatmentByDepartment(@Param("department") Integer department);

    @Query(value = "SELECT * FROM treatment "+
            "WHERE room_id=:room AND treatment.check_out_date IS NULL",
            nativeQuery = true)
    List<Treatment> getTreatmentByRoom(@Param("room") Integer room);

    @Query(value = "SELECT treatment.* FROM treatment "+
            "JOIN specialist ON specialist.id=treatment.specialist_id "+
            "WHERE specialist.organization_id=:organization " +
            "AND treatment.enter_date BETWEEN :start AND :end " +
            "AND treatment.check_out_date BETWEEN :start AND :end",
            nativeQuery = true)
    List<Treatment> getOldTreatmentByOrganization(@Param("organization") Integer organization,
                                                  @Param("start") String start,
                                                  @Param("end") String end);

    @Query(value = "SELECT treatment.* FROM treatment "+
            "WHERE treatment.specialist_id=:specialist " +
            "AND treatment.enter_date BETWEEN :start AND :end " +
            "AND treatment.check_out_date BETWEEN :start AND :end",
            nativeQuery = true)
    List<Treatment> getOldTreatmentBySpecialist(@Param("specialist") Integer specialist,
                                                  @Param("start") String start,
                                                  @Param("end") String end);

    @Query(value = "SELECT treatment.* FROM treatment "+
            "JOIN specialist ON specialist.id=treatment.specialist_id "+
            "WHERE specialist.type_id=:type " +
            "AND treatment.check_out_date IS NULL",
            nativeQuery = true)
    List<Treatment> getTreatmentsBySpecialistType(@Param("type") Integer type);

    @Query(value = "SELECT treatment.* FROM treatment "+
            "WHERE treatment.specialist_id=:specialist " +
            "AND treatment.check_out_date IS NULL",
            nativeQuery = true)
    List<Treatment> getTreatmentsBySpecialist(@Param("specialist") Integer specialist);

}
