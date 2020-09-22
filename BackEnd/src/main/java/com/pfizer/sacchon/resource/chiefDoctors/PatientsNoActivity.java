package com.pfizer.sacchon.resource.chiefDoctors;

import com.pfizer.sacchon.representation.PatientRepresentation;
import com.pfizer.sacchon.representation.RepresentationResponse;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientsNoActivity {

    @Get("json")
    RepresentationResponse<List<PatientRepresentation>> patientsWithNoActivity();
}
