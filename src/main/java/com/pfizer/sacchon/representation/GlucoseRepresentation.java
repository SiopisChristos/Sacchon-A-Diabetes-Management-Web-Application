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
    private long patient_id;

    public GlucoseRepresentation(Glucose glucose) {
        if (glucose != null) {
            dateTime = glucose.getDateTime();
            measurement = glucose.getMeasurement();
        }
    }
}
