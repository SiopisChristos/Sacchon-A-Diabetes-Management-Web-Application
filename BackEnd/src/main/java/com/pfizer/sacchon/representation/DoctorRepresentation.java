package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Doctor;
import com.pfizer.sacchon.model.Patient;
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
    public Doctor createDoctor() {
        Doctor doctor = new Doctor();
        doctor.setUsername(username);
        doctor.setLastName(lastName);
        doctor.setFirstName(firstName);
        doctor.setDateOfBirth(dateOfBirth);
        doctor.setSpecialty(specialty);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setActive(true);
        return doctor;
    }

}
