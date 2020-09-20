package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface DoctorUtilities {

    @Get("json")
    List<PatientRepresentation> getMyPatients();
}
