package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface GlucoseListResource {

    /**
     * Patients can view their average daily blood glucose level over a user- specified period
     * @return the average of carb intake per day as list
     * @throws NotFoundException if there are NO entries
     */
    @Get("json")
    List<GlucoseRepresentation> getAverageGlucoseLevel() throws NotFoundException;
}
