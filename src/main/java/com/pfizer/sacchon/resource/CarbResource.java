package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import org.restlet.resource.Post;

public interface CarbResource {

    /**
     * The patient can store their data carb intake (measured in grams) *REQUIRED*
     * @param carbRepresentationIn  representation of a Carb given by the frontEnd
     * @return  a representation of the persisted object
     * @throws BadEntityException
     */
    @Post("json")
    CarbRepresentation addCarbEntry(CarbRepresentation carbRepresentationIn);

}
