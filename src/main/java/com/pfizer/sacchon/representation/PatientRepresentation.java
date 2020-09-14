package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Patient;

import java.util.Date;

public class PatientRepresentation {
    private String username;
   // private String password;
    private Date dateOfBirth;
    private boolean active;
   // The URL of this resource.
    private String uri;


    public PatientRepresentation(
            Patient patient) {
        if (patient != null) {
            username = patient.getUsername();
     //       password = patient.getPassword();
            dateOfBirth = patient.getDateOfBirth();
            uri = "http://localhost:9000/v1/patient/" + patient.getId();
        }
    }

    public Patient createPatient() {
        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setActive(true);
       // patient.setPassword(password);
        patient.setDateOfBirth(dateOfBirth);

        return patient;
    }

}
