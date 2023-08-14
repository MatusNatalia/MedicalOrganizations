package ru.nsu.g20202.nmatus.medicalorg.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.g20202.nmatus.medicalorg.services.UserService;

@RestController
@CrossOrigin
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.OPTIONS,value = "/login")
    public ResponseEntity<?> Login() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.POST, HttpMethod.OPTIONS)
                .build();
    }

}
