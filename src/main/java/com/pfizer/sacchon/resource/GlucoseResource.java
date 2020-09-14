package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.GlucoseRepresentation;
import org.restlet.resource.Post;

public interface GlucoseResource {

    //The patient can store their data blood glucose level (date, time, measured in mg/dL)
    @Post("json")
    GlucoseRepresentation addGlucose(GlucoseRepresentation glucoseRepresentationIn);
}
