package com.pfizer.sacchon.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id; /** Technical identifier.  primary key */
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private Date creationDate = new Date();
    private String specialty;
    @ColumnDefault("1")
    private boolean isActive = true;

    // related column between Doctor and UserTable
    @Column(unique = true)
    private String username;


    @OneToMany (mappedBy = "doctor")
    private List<Patient> patientList = new ArrayList<>();

    @OneToMany(mappedBy = "doctor")
    private List<Note> noteList = new ArrayList<>();
}
