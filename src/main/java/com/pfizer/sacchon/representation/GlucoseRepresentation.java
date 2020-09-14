package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Glucose;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GlucoseRepresentation {
    private Date dateTime;
    private double measurement;
    private String uri;

    public GlucoseRepresentation(Glucose glucose) {
        if (glucose != null) {
            dateTime = glucose.getDateTime();
            measurement = glucose.getMeasurement();
            uri = "http://localhost:9000/v1/patient/glucose/" + glucose.getId();
        }
    }

    public Glucose createGlucose() {
        Glucose glucose = new Glucose();
        glucose.setDateTime(dateTime);
        glucose.setMeasurement(measurement);
        return glucose;
    }
}
