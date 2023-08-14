package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Department;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}

