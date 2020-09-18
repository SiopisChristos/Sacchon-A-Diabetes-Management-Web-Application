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
    private long id;
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
          id=patient.getId();
            uri = "http://localhost:9000/v1/patient/" + patient.getId();
        }
    }

//    public Patient createPatient() {
//        Patient patient = new Patient();
//        patient.setUsername(username);
//        patient.setActive(true);
//        patient.setPhoneNumber(phoneNumber);
//
//        return patient;
//    }
//
//
//    public Patient updatePatient(){
//        Patient p=new Patient();
//        p.setUsername(username);
//        p.setActive(true);
//        return p;
//    }

}
