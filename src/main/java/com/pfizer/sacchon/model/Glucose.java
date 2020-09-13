package com.pfizer.sacchon.model;

import javax.persistence.*;

import java.util.Date;

@Entity
public class Glucose {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private Date dateTime;
    private double concentration;
    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
}
