package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GlucoseRepresentation {
    private Date dateTime;
    private double measurement;
    private Patient patient;
    private String uri;
    private long patient_id;

    public GlucoseRepresentation(Glucose glucose) {
        if (glucose != null) {
            dateTime = glucose.getDateTime();
            measurement = glucose.getMeasurement();
            patient = glucose.getPatient();
            uri = "http://localhost:9000/v1/patient/glucose/" + glucose.getId();
        }
    }

    public Glucose createGlucose() {
        Glucose glucose = new Glucose();
        glucose.setDateTime(dateTime);
        glucose.setMeasurement(measurement);
        glucose.setPatient(patient);
        return glucose;
    }
}
