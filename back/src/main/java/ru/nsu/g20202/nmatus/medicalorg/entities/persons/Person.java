package ru.nsu.g20202.nmatus.medicalorg.entities.persons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name= "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Введите имя")
    @Size(min = 1, max = 100, message = "Имя должно содержать 1-100 символов")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Введите фамилию")
    @Size(min = 1, max = 100, message = "Фамилия должна содержать 1-100 символов")
    private String surname;

    public Person() {

    }

}
