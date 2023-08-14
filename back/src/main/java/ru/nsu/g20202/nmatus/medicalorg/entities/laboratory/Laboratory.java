package ru.nsu.g20202.nmatus.medicalorg.entities.laboratory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;
import ru.nsu.g20202.nmatus.medicalorg.entities.types.LaboratoryType;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "laboratory")
public class Laboratory {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Пустое название")
    @Size(min = 1, max = 255, message = "Название организации должно содержать 1-255 символов")
    private String name;

    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{7,11}$", message = "Несуществующий телефонный номер")
    private String number;

    @ManyToOne
    private LaboratoryType type;

    @ManyToMany(mappedBy = "laboratories")
    private List<Organization> organizations;

    @OneToMany(mappedBy = "laboratory")
    private List<Study> studies;
}
