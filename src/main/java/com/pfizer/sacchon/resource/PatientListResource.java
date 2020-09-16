package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientListResource {

    @Get("json")
    List<PatientRepresentation> getListPatients() throws NotFoundException;
}
