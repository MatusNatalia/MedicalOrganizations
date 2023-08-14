package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.DiseaseType;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "department")
public class Department {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Пустое название")
    @Size(min = 1, max = 255, message = "Название отделения должно содержать 1-255 символов")
    private String name;

    @ManyToOne(optional = false)
    private DiseaseType type;

    @ManyToOne(optional = false)
    private Block block;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Room> rooms;
}
