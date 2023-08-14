package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.dtos.RoleDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.UserDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.laboratory.LaboratoryDto;
import ru.nsu.g20202.nmatus.medicalorg.services.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    Iterable<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    List<String> getUserRoles(@RequestParam String username) {
        return userService.getUserRoles(username);
    }

    @PostMapping("/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    void addUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @DeleteMapping("/users/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
