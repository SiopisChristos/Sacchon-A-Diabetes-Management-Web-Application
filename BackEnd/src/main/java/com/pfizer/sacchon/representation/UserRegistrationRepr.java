package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.model.UserTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import java.util.Date;

@NoArgsConstructor
@Data
public class UserRegistrationRepr {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private Date creationDate;
    private String specialty;
    private String address;
    private String city;
    private String zipCode;
    private String username;
    private String password;
    private String role;

    public Doctor createDoctor(){
        Doctor doctor = new Doctor();
        doctor.setUsername(username);
        doctor.setDateOfBirth(dateOfBirth);
        doctor.setLastName(lastName);
        doctor.setFirstName(firstName);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setSpecialty(specialty);
        return doctor;
    }

    public Patient createPatient(){
        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setZipCode(zipCode);
        patient.setAddress(address);
        patient.setCity(city);
        patient.setDateOfBirth(dateOfBirth);
        patient.setLastName(lastName);
        patient.setFirstName(firstName);
        patient.setPhoneNumber(phoneNumber);
        return patient;
    }

    public UserTable createUser(){
        UserTable user = new UserTable();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

}
