package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.model.Carb;
import com.pfizer.sacchon.representation.PatientRepresentation;
//import javassist.NotFoundException;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.exception.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientResource {
    @Get("json")
    PatientRepresentation getPatient() throws NotFoundException;

    @Post("json")
    PatientRepresentation addPatient(PatientRepresentation patientRepresentationIn) throws  BadEntityException;

    @Delete
    void deletePatient() throws NotFoundException;
    //set isActive as false;

    @Put("json")
    PatientRepresentation updatePatient(PatientRepresentation patientRepresentationIn)  throws BadEntityException ;

}
