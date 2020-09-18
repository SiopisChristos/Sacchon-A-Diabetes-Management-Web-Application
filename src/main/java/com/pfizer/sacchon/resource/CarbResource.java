package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.CarbRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.sql.Date;
import java.util.Optional;

public interface CarbResource {

    //Returns carb entry id (just for testing)
//    @Get("json")
//    CarbRepresentation getCarbEntry() throws NotFoundException;

    //The patient can store their data carb intake (measured in grams)
    @Post("json")
    CarbRepresentation addCarb(CarbRepresentation carbRepresentationIn);

    //The patient can view their average carb intake over a user-specified period
    @Get("json")
    Double getAverageCarbIntake(Long id) throws NotFoundException;

}
