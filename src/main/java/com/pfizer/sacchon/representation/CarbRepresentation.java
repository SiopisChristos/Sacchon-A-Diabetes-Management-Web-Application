package com.pfizer.sacchon.representation;

import com.pfizer.sacchon.model.Carb;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CarbRepresentation {
    private long gram;
    private Date date;

    public CarbRepresentation(Carb carb) {
        if (carb != null) {
            gram = carb.getGram();
            date = carb.getDate();
        }
    }
}
