package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface CarbListResource {

    /**
     * The patient can store their data carb intake (measured in grams) *REQUIRED*
     * @param carbRepresentationIn  representation of a Carb given by the frontEnd
     * @return  a representation of the persisted object
     * @throws BadEntityException
     */
    @Post("json")
    RepresentationResponse<Boolean> addCarbEntry(CarbRepresentation carbRepresentationIn) throws BadEntityException;

    /**
     * Patients can view their average carb intake over a user- specified period
     * @return the average of carbs intake per day as list
     * @throws NotFoundException if there are NO entries
     */
    @Get("json")
    RepresentationResponse<Double> getAverageCarbIntake() throws NotFoundException;
}
