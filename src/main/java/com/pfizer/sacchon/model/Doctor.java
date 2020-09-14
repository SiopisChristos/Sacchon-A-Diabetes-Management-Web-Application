package com.pfizer.sacchon.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String username;
    private Date dateOfBirth;
    private String speciality;
    private boolean active;

    @OneToMany (mappedBy = "doctor")
    private List<Patient> patientList = new ArrayList<>();

    @OneToMany(mappedBy = "doctor")
    private List<Note> noteList = new ArrayList<>();
}
