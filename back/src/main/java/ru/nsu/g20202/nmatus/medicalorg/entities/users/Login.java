package ru.nsu.g20202.nmatus.medicalorg.entities.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "persistent_login")
public class Login {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name="username")
    private String username;
}
