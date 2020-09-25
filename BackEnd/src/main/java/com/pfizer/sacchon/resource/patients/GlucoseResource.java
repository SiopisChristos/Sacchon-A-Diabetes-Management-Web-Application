package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.GlucoseRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface GlucoseResource {

    /**
     * Deletes a glucose entry from the Database
     *
     * @return RepresentationResponse
     * @throws NotFoundException
     */
    @Delete
    RepresentationResponse<Boolean> removeGlucoseEntry() throws NotFoundException;

    /**
     * Update data of an existing glucose entry
     *
     * @param glucoseRepresentationIn
     * @return boolean
     * @throws BadEntityException
     */
    @Put("json")
    RepresentationResponse<Boolean> updateGlucoseEntry(GlucoseRepresentation glucoseRepresentationIn) throws BadEntityException;

}
