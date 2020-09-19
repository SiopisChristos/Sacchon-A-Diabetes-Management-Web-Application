package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.*;

public interface PatientResource {
    @Get("json")
    RepresentationResponse<PatientRepresentation> getPatient() throws NotFoundException;

    @Delete
    RepresentationResponse<Boolean>
        removePatient() throws NotFoundException;

    @Put("json")
    RepresentationResponse<Boolean>
        updatePatient(PatientRepresentation patientRepresentationIn) throws BadEntityException;

    @Post("json")
    RepresentationResponse<PatientRepresentation>
        addPatient(PatientRepresentation patientRepresentationIn) throws BadEntityException;

    @Patch
    RepresentationResponse<Boolean> deletePatient() throws NotFoundException;

}
