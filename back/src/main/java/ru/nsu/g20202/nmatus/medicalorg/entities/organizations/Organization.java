package ru.nsu.g20202.nmatus.medicalorg.entities.organizations;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Laboratory;
import ru.nsu.g20202.nmatus.medicalorg.entities.laboratory.Study;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Specialist;
import ru.nsu.g20202.nmatus.medicalorg.entities.persons.Stuff;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "organization")
@Inheritance(strategy = InheritanceType.JOINED)
public class Organization {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Пустое название")
    @Size(min = 1, max = 255, message = "Название организации должно содержать 1-255 символов")
    private String name;

    @Column(name = "phone_number")
    @NotBlank(message = "Введите телефонный номер")
    @Pattern(regexp = "^[0-9]{7,11}$", message = "Несуществующий телефонный номер")
    private String number;

    @OneToMany(mappedBy = "organization")
    private List<Specialist> specialists;

    @OneToMany(mappedBy = "organization")
    private List<Stuff> stuff;

    @ManyToMany
    @JoinTable(
            name = "organization_laboratory",
            joinColumns = { @JoinColumn(name = "organization_id") },
            inverseJoinColumns = { @JoinColumn(name = "laboratory_id") }
    )
    private List<Laboratory> laboratories;

    @OneToMany(mappedBy = "organization")
    private List<Study> studies;

    public Organization(String name, String number){
        this.name = name;
        this.number = number;
    }

    public Organization() {

    }
}
