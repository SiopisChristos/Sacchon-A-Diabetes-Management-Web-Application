package com.pfizer.sacchon.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String username;
    private Date dateOfBirth;
    private boolean active;

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
