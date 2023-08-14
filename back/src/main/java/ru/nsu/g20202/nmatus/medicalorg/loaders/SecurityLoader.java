package ru.nsu.g20202.nmatus.medicalorg.loaders;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nsu.g20202.nmatus.medicalorg.entities.users.Role;
import ru.nsu.g20202.nmatus.medicalorg.entities.users.User;
import ru.nsu.g20202.nmatus.medicalorg.repositories.RoleRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.UserRepository;
import ru.nsu.g20202.nmatus.medicalorg.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityLoader {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SecurityLoader(UserRepository userRepository,
                          RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

        /*Role userRole = new Role();
        userRole.setName("USER");

        Role adminRole = new Role();
        adminRole.setName("ADMIN");

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(roleRepository.findByName("USER").get(0));
        adminRoles.add(roleRepository.findByName("ADMIN").get(0));

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword(pwEncoder.encode("1234"));
        admin.setRoles(adminRoles);

        userRepository.save(admin);*/
    }

}
