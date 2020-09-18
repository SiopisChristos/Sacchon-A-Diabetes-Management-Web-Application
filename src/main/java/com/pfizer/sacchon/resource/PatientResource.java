package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.PatientRepresentation;
//import javassist.NotFoundException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;

import java.util.List;

public interface PatientResource {
    @Get("json")
    PatientRepresentation getPatient() throws NotFoundException;

    @Delete
    Boolean removePatient() throws NotFoundException;
    //set isActive as false;

    @Put("json")
    Boolean updatePatient(PatientRepresentation patientRepresentationIn)  throws BadEntityException ;

    @Post("json")
    PatientRepresentation addPatient(PatientRepresentation patientRepresentationIn) throws  BadEntityException;

    @Put
    Boolean deletePatient() throws NotFoundException;

}
