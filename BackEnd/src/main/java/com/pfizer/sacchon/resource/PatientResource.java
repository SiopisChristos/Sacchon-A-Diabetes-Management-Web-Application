package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.*;

public interface PatientResource {
    @Get("json")
    RepresentationResponse<PatientRepresentation> getPatient() throws NotFoundException;

    //Removes completely the patient from the Database
    @Delete
    RepresentationResponse<Boolean>
        removePatient() throws NotFoundException;

    //Update personal info
    @Put("json")
    RepresentationResponse<Boolean>
        updatePatient(PatientRepresentation patientRepresentationIn) throws BadEntityException;

    @Post("json")
    RepresentationResponse<PatientRepresentation>
        addPatient(PatientRepresentation patientRepresentationIn) throws BadEntityException;

    //Sets the patient as inactive but details still remain in the Database
    @Patch
    RepresentationResponse<Boolean> deletePatient() throws NotFoundException;

}
