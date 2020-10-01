package com.pfizer.sacchon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String message;
    private Date date;
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean seen = false;

    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;

}
