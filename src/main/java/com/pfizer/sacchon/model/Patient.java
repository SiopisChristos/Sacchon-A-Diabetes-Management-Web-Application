package com.pfizer.sacchon.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @ColumnDefault("1")
    private boolean isActive = true;
    private Date creationDate = new Date();

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
