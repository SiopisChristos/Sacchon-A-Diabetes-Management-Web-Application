package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.model.Patient;
import com.pfizer.sacchon.resource.CarbResourceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.restlet.engine.Engine;

import java.util.Date;
import java.util.logging.Logger;

@Data
@NoArgsConstructor
public class CarbRepresentation {
    private long gram;
    private Date date;
    private Patient patient;
    private String uri;

    public static final Logger LOGGER = Engine.getLogger(CarbRepresentation.class);

    public CarbRepresentation(Carb carb) {
        if (carb != null) {
            gram = carb.getGram();
            date = carb.getDate();
            patient = carb.getPatient();
            uri = "http://localhost:9000/v1/patient/carb/" + carb.getId();
        }
    }


    public Carb createCarb() {
        Carb carb = new Carb();
        carb.setGram(gram);
        carb.setDate(date);
        carb.setPatient(patient);
        return carb;
    }
}
