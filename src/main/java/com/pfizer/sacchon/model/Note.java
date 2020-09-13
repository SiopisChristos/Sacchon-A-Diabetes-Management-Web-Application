package com.pfizer.sacchon.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String message;
    private Date date;

    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;

}
