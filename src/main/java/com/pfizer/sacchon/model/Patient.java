package com.pfizer.sacchon.model;

import com.pfizer.sacchon.resource.PatientResourceImpl;
import lombok.Data;
import org.restlet.engine.Engine;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;  /** Technical identifier.  primary key */
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private Date dateOfBirth;
    private boolean isActive;

    // related column between Patient and UserTable
    @Column(unique = true)
    private String username;


    @OneToMany(mappedBy = "patient",  cascade = CascadeType.ALL)
    private List<Glucose> glucoseList = new ArrayList<>();

    @OneToMany(mappedBy = "patient",  cascade = CascadeType.ALL)
    private List<Carb> carbList = new ArrayList<>();

    @OneToMany(mappedBy = "patient",  cascade = CascadeType.ALL)
    private List<Note> noteList = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;


}
