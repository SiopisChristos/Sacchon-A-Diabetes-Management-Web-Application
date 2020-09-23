package com.pfizer.sacchon.resource.doctors;

import com.pfizer.sacchon.exception.BadEntityException;
import com.pfizer.sacchon.representation.DoctorRepresentation;
import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface DoctorUtilities {

    @Get("json")
    RepresentationResponse<List<PatientRepresentation>> getMyPatients();


    @Post("json")
    RepresentationResponse<DoctorRepresentation>
    addDoctor(DoctorRepresentation doctorRepresentationIn) throws BadEntityException;
}