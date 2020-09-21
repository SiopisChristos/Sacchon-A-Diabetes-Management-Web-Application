package com.pfizer.sacchon.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Glucose {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private Date dateTime;
    private double measurement;
    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
}
