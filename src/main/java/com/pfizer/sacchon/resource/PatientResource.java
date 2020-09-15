package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.PatientRepresentation;
//import javassist.NotFoundException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;
public interface PatientResource {
    @Get("json")
    PatientRepresentation getPatient() throws NotFoundException;

    @Delete
    void remove() throws NotFoundException;
    //set isActive as false;

    @Put("json")
    PatientRepresentation createPatient(PatientRepresentation patientRepresentationIn)  throws BadEntityException ;


}
