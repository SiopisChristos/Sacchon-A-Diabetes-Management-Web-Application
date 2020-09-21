package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Doctor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DoctorRepresentation {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private Date dateOfBirth;
    private String speciality;

    public DoctorRepresentation(Doctor doctor) {
        firstName = doctor.getFirstName();
        lastName = doctor.getLastName();
        phoneNumber = doctor.getPhoneNumber();
        username = doctor.getUsername();
        dateOfBirth = doctor.getDateOfBirth();
        speciality = doctor.getSpeciality();
    }
}
