package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Glucose;
import com.pfizer.sacchon.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GlucoseRepresentation {
    private long id;
    private Date dateTime;
    private double measurement;
    private long patient_id;
    private String uri;

    public GlucoseRepresentation(Glucose glucose) {
        if (glucose != null) {
            id = glucose.getId();
            dateTime = glucose.getDateTime();
            measurement = glucose.getMeasurement();
            patient_id = glucose.getPatient().getId();
            uri = "http://localhost:9000/v1/patient/glucose/" + glucose.getId();
        }
    }

    public Glucose createGlucose(Patient patient) {
        Glucose glucose = new Glucose();
        glucose.setDateTime(dateTime);
        glucose.setMeasurement(measurement);
        glucose.setPatient(patient);
        return glucose;
    }
}
