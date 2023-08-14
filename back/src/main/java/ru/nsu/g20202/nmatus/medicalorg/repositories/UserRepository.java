package ru.nsu.g20202.nmatus.medicalorg.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.g20202.nmatus.medicalorg.entities.users.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByLogin(String login);
}

