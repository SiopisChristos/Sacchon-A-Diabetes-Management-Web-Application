package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;

import java.util.List;

public interface DoctorUtilities {

    @Get("json")
    RepresentationResponse<List<PatientRepresentation>> getMyPatients();
}
