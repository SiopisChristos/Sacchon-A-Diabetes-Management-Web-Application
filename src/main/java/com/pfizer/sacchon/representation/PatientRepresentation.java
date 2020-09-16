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
    private String zipCode;
    private String phoneNumber;
    private String username;
    private Date dateOfBirth;
    private Doctor doctorId;
    private boolean isActive;
    /**
     * The URL of this resource.
     */
    private String uri;


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
            doctorId = patient.getDoctor();
            isActive= true;
            uri = "http://localhost:9000/v1/patient/" + patient.getId();
        }
    }

    public Patient createPatient() {
        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAddress(address);
        patient.setCity(city);
        patient.setPhoneNumber(phoneNumber);
        patient.setZipCode(zipCode);
        patient.setDoctor(getDoctorId());
        patient.setActive(true);
        return patient;
    }

}
