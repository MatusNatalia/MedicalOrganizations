package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "room")
public class Room {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "number")
    @NotNull
    private String number;

    @Column(name = "size")
    @NotNull
    private Integer size;

    @Column(name = "busy")
    @NotNull
    private Integer busy;

    @Column(name = "free")
    @NotNull
    private Boolean free;

    @ManyToOne(optional = false)
    private Department department;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Bed> beds;

    @OneToMany(mappedBy = "room")
    private List<Treatment> treatments;
}
