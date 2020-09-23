package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface GlucoseListResource {

    /**
     * The patient can store their blood glucose level (date, time, measured in mg/dL) *REQUIRED*
     * @param glucoseRepresentationIn  representation of a Glucose given by the frontEnd
     * @return  a representation of the persisted object
     * @throws BadEntityException
     */
    @Post("json")
    Boolean addGlucoseEntry(GlucoseRepresentation glucoseRepresentationIn) throws BadEntityException;

    /**
     * Patients can view their average daily blood glucose level over a user- specified period
     * @return the average of carb intake per day as list
     * @throws NotFoundException if there are NO entries
     */
    @Get("json")
    List<GlucoseRepresentation> getAverageGlucoseLevel() throws NotFoundException;
}
