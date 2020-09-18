package com.pfizer.sacchon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class Carb {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long gram;
    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Date date;
    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
}
