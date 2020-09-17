package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Carb;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CarbRepresentation {
    private double gram;
    private Date date;
    private long patient_id;

    public CarbRepresentation(Carb carb) {
        if (carb != null) {
            gram = carb.getGram();
            date = carb.getDate();
        }
    }
}
