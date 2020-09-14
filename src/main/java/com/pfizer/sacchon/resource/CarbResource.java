package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.CarbRepresentation;
import org.restlet.resource.Post;

public interface CarbResource {

    //The patient can store their data carb intake (measured in grams)
    @Post("json")
    CarbRepresentation addCarb(CarbRepresentation carbRepresentationIn);

}
