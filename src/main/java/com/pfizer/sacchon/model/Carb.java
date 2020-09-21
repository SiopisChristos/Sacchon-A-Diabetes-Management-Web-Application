package com.pfizer.sacchon.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Carb {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private long gram;
    private Date date;
    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;
}
