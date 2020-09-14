package com.pfizer.sacchon.resource;

import com.pfizer.sacchon.representation.PatientRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface PatientResource {
    @Get("json")
    public PatientResource getPatient();

    @Delete
    public void remove() ;
    //set isActive as false;

    @Put("json")
    public PatientResource createPatient(PatientRepresentation patient);


}
