package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRepresentation {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private String username;
    private Date dateOfBirth;


    public PatientRepresentation(Patient patient) {
        if (patient != null) {
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            address = patient.getAddress();
            city = patient.getCity();
            zipCode = patient.getZipCode();
            phoneNumber = patient.getPhoneNumber();
            username = patient.getUsername();
            dateOfBirth = patient.getDateOfBirth();
        }
    }

}
