package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Doctor;
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
    private String phoneNumber;
    private String username;
    private Date dateOfBirth;
    private long doctor_id;
    private String zipCode;



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
            //       id=patient.getId();
            if (patient.getDoctor() != null)
                doctor_id = patient.getDoctor().getId();
            else
                doctor_id = -1;

        }
    }

    public Patient createPatient() {
        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setLastName(lastName);
        patient.setFirstName(firstName);
        patient.setDateOfBirth(dateOfBirth);
        patient.setAddress(address);
        patient.setPhoneNumber(phoneNumber);
        patient.setCity(city);
        patient.setActive(true);
        return patient;
    }


    public static Patient updatePatient(PatientRepresentation patientRep) {
        Patient patientIn = new Patient();
        if (patientRep.getFirstName() != null) {
            patientIn.setFirstName(patientRep.getFirstName());
        }
        if (patientRep.getLastName() != null) {
            patientIn.setLastName(patientRep.getLastName());
        }
        if (patientRep.getUsername() != null) {
            patientIn.setUsername(patientRep.getUsername());
        }
        if (patientRep.getAddress() != null) {
            patientIn.setAddress(patientRep.getAddress());
        }
        if (patientRep.getCity() != null) {
            patientIn.setCity(patientRep.getCity());
        }
        if (patientRep.getDateOfBirth() != null) {
            patientIn.setDateOfBirth(patientRep.getDateOfBirth());
        }
        if (patientRep.getPhoneNumber() != null) {
            patientIn.setPhoneNumber(patientRep.getPhoneNumber());
        }
        return patientIn;
    }

    public static PatientRepresentation initData(Patient newPatient) {
        PatientRepresentation result =
                new PatientRepresentation();
        result.setUsername(newPatient.getUsername());
        result.setFirstName(newPatient.getFirstName());
        result.setLastName(newPatient.getLastName());
        result.setAddress(newPatient.getAddress());
        result.setPhoneNumber(newPatient.getPhoneNumber());
        result.setCity(newPatient.getCity());


        return result;
    }
}


