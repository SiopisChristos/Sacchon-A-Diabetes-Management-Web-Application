package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class CarbRepresentation {
    private double gram;
    private Date date;
    private long patient_id;
    private String uri;

    public CarbRepresentation(Carb carb) {
        if (carb != null) {
            gram = carb.getGram();
            date = carb.getDate();
            patient_id = carb.getPatient().getId();
            uri = "http://localhost:9000/v1/patient/carb/" + carb.getId();
        }
    }

    public Carb createCarb(Patient p) {
        Carb carb = new Carb();
        carb.setGram(gram);
        carb.setDate(date);
        carb.setPatient(p);
        return carb;
    }
}
