package com.pfizer.sacchon.resource.patients;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.*;

public interface PatientResource {


    //Update personal info
    @Put("json")
    RepresentationResponse<Boolean>
        updatePatient(PatientRepresentation patientRepresentationIn) throws BadEntityException;

    @Post("json")
    RepresentationResponse<PatientRepresentation>
        addPatient(PatientRepresentation patientRepresentationIn) throws BadEntityException;

    //Sets the patient as inactive but details still remain in the Database
   @Delete
   RepresentationResponse<Boolean> deletePatient() throws NotFoundException;

}
