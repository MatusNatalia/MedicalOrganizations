package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Cabinet;
import ru.nsu.g20202.nmatus.medicalorg.entities.hospital.Block;

@Repository
public interface BlockRepository extends CrudRepository<Block, Integer> {

}

