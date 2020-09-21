package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface CarbResource {

    /**
     * Deletes a carb entry from the Database
     *
     * @return RepresentationResponse
     * @throws NotFoundException
     */
    @Delete
    RepresentationResponse<Boolean> removeCarbEntry() throws NotFoundException;

    /**
     * Update data of an existing carb entry
     *
     * @param carbRepresentationIn
     * @return boolean
     * @throws BadEntityException
     */
    @Put("json")
    CarbRepresentation storeCarbEntry(CarbRepresentation carbRepresentationIn) throws BadEntityException;

}
