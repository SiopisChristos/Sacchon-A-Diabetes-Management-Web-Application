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
    private String password;
    private Date dateOfBirth;
    private String speciality;

    @OneToMany (mappedBy = "doctor")
    private List<Patient> patientList = new ArrayList<>();

    @OneToMany(mappedBy = "doctor")
    private List<Note> noteList = new ArrayList<>();
}
