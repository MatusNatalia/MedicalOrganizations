package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.users.Role;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findByName(String name);
}

