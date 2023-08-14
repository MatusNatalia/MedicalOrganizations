package ru.nsu.g20202.nmatus.medicalorg.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String login;
    private String password;
    private List<String> roles;
}
