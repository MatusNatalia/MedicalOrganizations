package ru.nsu.g20202.nmatus.medicalorg.entities.hospital;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.g20202.nmatus.medicalorg.entities.clinic.Clinic;
import ru.nsu.g20202.nmatus.medicalorg.entities.organizations.Organization;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "hospital")
public class Hospital extends Organization {

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.REMOVE)
    List<Block> blocks;

    @OneToMany(mappedBy = "hospital")
    List<Clinic> clinics;

    public Hospital(String name, String number){
        super(name, number);
    }

    public Hospital() {
        super();
    }
}
