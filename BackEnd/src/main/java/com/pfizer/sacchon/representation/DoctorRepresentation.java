package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Doctor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class DoctorRepresentation {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private Date dateOfBirth;
    private String dateOfBirthString;
    private String specialty;

    public DoctorRepresentation(Doctor doctor) {
        firstName = doctor.getFirstName();
        lastName = doctor.getLastName();
        phoneNumber = doctor.getPhoneNumber();
        username = doctor.getUsername();
        dateOfBirth = doctor.getDateOfBirth();
        specialty = doctor.getSpecialty();
        dateOfBirth = doctor.getDateOfBirth() ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dateOfBirthString = sdf.format(doctor.getDateOfBirth() );

    }
}
