package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "block")
public class Block {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Пустое название")
    @Size(min = 1, max = 255, message = "Название блока должно содержать 1-255 символов")
    private String name;

    @Column(name = "address")
    @NotBlank(message = "Пустой адрес")
    @Size(min = 1, max = 255, message = "Адрес должен содержать 1-255 символов")
    private String address;

    @ManyToOne(optional = false)
    private Hospital hospital;

    @OneToMany(mappedBy = "block", cascade = CascadeType.REMOVE)
    private List<Department> departments;
}
