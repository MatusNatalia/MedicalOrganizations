package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Hospital;

import java.util.List;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital, Integer> {

    List<Hospital> findByName(String name);

    @Query(value = "SELECT COUNT(*) FROM room "+
            "JOIN department ON department.id=room.department_id " +
            "JOIN block ON block.id=department.block_id " +
            "WHERE block.hospital_id=:id",
            nativeQuery = true)
    Integer getNumberOfRooms(@Param("id") Integer id);

    @Query(value = "SELECT COUNT(*) FROM bed " +
            "JOIN room ON room.id=bed.room_id " +
            "JOIN department ON department.id=room.department_id " +
            "JOIN block ON block.id=department.block_id " +
            "WHERE block.hospital_id=:id",
            nativeQuery = true)
    Integer getNumberOfBeds(@Param("id") Integer id);

    @Query(value = "SELECT COUNT(*) FROM room "+
            "WHERE room.department_id=:id AND room.free=true",
            nativeQuery = true)
    Integer getNumberOfFreeRooms(@Param("id") Integer id);

    @Query(value = "SELECT SUM(room.size-room.busy) FROM room " +
            "WHERE room.department_id=:id",
            nativeQuery = true)
    Integer getNumberOfFreeBeds(@Param("id") Integer id);

}