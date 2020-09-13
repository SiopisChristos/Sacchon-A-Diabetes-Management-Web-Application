package com.pfizer.sacchon.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private Date dateOfBirth;

    @OneToMany(mappedBy = "patient")
    private List<Glucose> glucoseList = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Carb> carbList = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<Note> noteList = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;
}
