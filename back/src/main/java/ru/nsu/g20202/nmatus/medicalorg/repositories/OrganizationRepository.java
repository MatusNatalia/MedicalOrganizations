package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
    Organization findByName(String name);
}

